package com.starterdemo.dagger;

import com.starterdemo.fetcher.*;
import com.starterdemo.persistence.UserDetailsDao;
import com.starterdemo.service.UserService;
import dagger.Module;
import dagger.Provides;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import javax.inject.Singleton;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Module
public class GraphQLModule {

    @Provides
    @Singleton
    UsersListDataFetcher getUserListDataFetcher(UserService userService)
    {
        return new UsersListDataFetcher(userService);
    }

    @Provides
    @Singleton
    UserDetailsDataFetcher getUserDetailsDataFetcher(UserService userService)
    {
        return new UserDetailsDataFetcher(userService);
    }

    @Provides
    @Singleton
    AddUserMutation getAddUserMutation(UserService userService)
    {
        return new AddUserMutation(userService);
    }

    @Provides
    @Singleton
    UpdateUserMutation getUpdateUserMutation(UserService userService)
    {
        return new UpdateUserMutation(userService);
    }

    @Provides
    @Singleton
    DeleteUserMutation getDeleteUserMutation(UserService userService)
    {
        return new DeleteUserMutation(userService);
    }

    @Provides
    @Singleton
    public GraphQL getGraphQL(UsersListDataFetcher usersListDataFetcher,
                              UserDetailsDataFetcher userDetailsDataFetcher,
                              AddUserMutation addUserMutation,
                              UpdateUserMutation updateUserMutation,
                              DeleteUserMutation deleteUserMutation
    )
    {
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                // this uses builder function lambda syntax
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("users", usersListDataFetcher)
                        .dataFetcher("userById", userDetailsDataFetcher)
                )
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("addUser", addUserMutation)
                        .dataFetcher("updateUser", updateUserMutation)
                        .dataFetcher("deleteUser", deleteUserMutation)
                )
                .build();

        return initGraphQL(wiring);
    }

    private GraphQL initGraphQL(RuntimeWiring wiring)
    {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        InputStream stream = GraphQL.class.getResourceAsStream("/schema.graphql");
        InputStreamReader reader = new InputStreamReader(stream, Charset.defaultCharset());

        TypeDefinitionRegistry typeRegistry = schemaParser.parse(reader);
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
