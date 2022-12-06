package com.starterdemo.Resources;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@Path("/graphql")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class GraphQLResource {

    private final GraphQL graphQL;

    @Inject
    public GraphQLResource(GraphQL graphQL){
        this.graphQL = graphQL;
    }

    @OPTIONS
    public Response handleCors()
    {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    @POST
    public Response processGraphQLQueries(final String bodyQuery) throws IOException {
        JSONObject bodyQueryJson = new JSONObject(bodyQuery);
        String graphQLQuery = bodyQueryJson.getString("query");
        Map variables = new ObjectMapper().readValue(bodyQueryJson.get("variables").toString(), Map.class);
        log.info(variables.toString());
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(graphQLQuery)
                .variables(variables)
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);
        return Response
                .ok(Map.of("data", executionResult.getData()))
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

}
