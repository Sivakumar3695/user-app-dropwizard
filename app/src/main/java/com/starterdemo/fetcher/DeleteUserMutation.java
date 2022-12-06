package com.starterdemo.fetcher;

import com.starterdemo.enums.ExceptionCode;
import com.starterdemo.model.CustomException;
import com.starterdemo.response.AddUpdateUserGraphQLResponse;
import com.starterdemo.service.UserService;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Map;

@Slf4j
public class DeleteUserMutation implements DataFetcher<DataFetcherResult<Map>> {

    private final UserService userService;

    @Inject
    public DeleteUserMutation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public DataFetcherResult<Map> get(DataFetchingEnvironment environment) throws Exception {

        try
        {
            String userId = environment.getArgument("user_id");
            userService.deleteUser(userId);

            return DataFetcherResult.<Map>newResult()
                    .data(Map.of("message", "User deleted"))
                    .build();
        }
        catch (CustomException e)
        {
            String errorMessage = "Invalid Request";
            if (e.getExceptionCode().errorCode == ExceptionCode.RESOURCE_NOT_FOUND.errorCode){
                log.error("Invalid user_id provided for update user details.");
                errorMessage = "Invalid user_id provided for update user details.";
            }
            GraphQLError error = GraphqlErrorBuilder.newError()
                    .message(errorMessage)
                    .errorType(ErrorType.DataFetchingException)
                    .build();

            return DataFetcherResult.<Map>newResult()
                    .error(error)
                    .build();

        }
        catch (Exception e)
        {
            log.error("Exception occurred while fetching the user list via graphQL. Exception details:" + e.getMessage());
            e.printStackTrace();

            GraphQLError error = GraphqlErrorBuilder.newError().message("Unable to process the request. Please try again.")
                    .errorType(ErrorType.DataFetchingException)
                    .build();

            return DataFetcherResult.<Map>newResult()
                    .error(error)
                    .build();
        }
    }
}
