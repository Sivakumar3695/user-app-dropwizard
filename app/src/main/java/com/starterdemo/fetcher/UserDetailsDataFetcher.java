package com.starterdemo.fetcher;

import com.starterdemo.model.User;
import com.starterdemo.service.UserService;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class UserDetailsDataFetcher implements DataFetcher<DataFetcherResult<User>> {

    private final UserService userService;

    @Inject
    public UserDetailsDataFetcher(UserService userService) {
        this.userService = userService;
    }


    @Override
    public DataFetcherResult<User> get(DataFetchingEnvironment environment) throws Exception {
        try
        {
            String user_id = environment.getArgument("user_id");
            User user = userService.getUserDetails(user_id);

            return DataFetcherResult.<User>newResult()
                    .data(user)
                    .build();
        }
        catch (Exception e)
        {
            log.error("Exception occurred while fetching the user list via graphQL. Exception details:" + e.getMessage());
            e.printStackTrace();

            GraphQLError error = GraphqlErrorBuilder.newError().message("Unable to process the request. Please try again.")
                    .errorType(ErrorType.DataFetchingException)
                    .build();

            return DataFetcherResult.<User>newResult()
                    .error(error)
                    .build();
        }

    }
}
