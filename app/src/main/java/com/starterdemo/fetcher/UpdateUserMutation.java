package com.starterdemo.fetcher;

import com.starterdemo.enums.ExceptionCode;
import com.starterdemo.model.CustomException;
import com.starterdemo.model.User;
import com.starterdemo.persistence.UserDetailsDao;
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
import java.util.UUID;

@Slf4j
public class UpdateUserMutation implements DataFetcher<DataFetcherResult<AddUpdateUserGraphQLResponse>> {

    private final UserService userService;

    @Inject
    public UpdateUserMutation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public DataFetcherResult<AddUpdateUserGraphQLResponse> get(DataFetchingEnvironment environment) throws Exception {

        try
        {
            Map userDetails = environment.getArgument("user");
            String userId = environment.getArgument("user_id");
            User user = userService.getUserDetails(userId);

            if (user == null) throw new CustomException(ExceptionCode.RESOURCE_NOT_FOUND);

            user.updateUser(new User(userDetails));
            User updatedUser = userService.updateUser(userId, user);

            return DataFetcherResult.<AddUpdateUserGraphQLResponse>newResult()
                    .data(new AddUpdateUserGraphQLResponse(updatedUser, "User details updated"))
                    .build();
        }
        catch (CustomException e)
        {
            log.error("Invalid user_id provided for update user details.");
            GraphQLError error = GraphqlErrorBuilder.newError().message("Invalid user_id provided for update user details.")
                    .errorType(ErrorType.DataFetchingException)
                    .build();

            return DataFetcherResult.<AddUpdateUserGraphQLResponse>newResult()
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

            return DataFetcherResult.<AddUpdateUserGraphQLResponse>newResult()
                    .error(error)
                    .build();
        }


    }
}
