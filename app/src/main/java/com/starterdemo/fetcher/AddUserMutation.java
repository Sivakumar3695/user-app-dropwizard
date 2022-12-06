package com.starterdemo.fetcher;

import com.starterdemo.model.User;
import com.starterdemo.persistence.UserDetailsDao;
import com.starterdemo.response.AddUpdateUserGraphQLResponse;
import com.starterdemo.service.UserService;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class AddUserMutation implements DataFetcher<DataFetcherResult<AddUpdateUserGraphQLResponse>> {

    private final UserService userService;

    @Inject
    public AddUserMutation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public DataFetcherResult<AddUpdateUserGraphQLResponse> get(DataFetchingEnvironment environment) throws Exception {

        Map userDetails = environment.getArgument("user");

        User user  = new User();
        user.setFirst_name((String) userDetails.get("first_name"));
        user.setLast_name((String) userDetails.get("last_name"));
        user.setEmail_id((String) userDetails.get("email_id"));

        User addedUser = userService.addNewUser(user);

        return DataFetcherResult.<AddUpdateUserGraphQLResponse>newResult()
                .data(new AddUpdateUserGraphQLResponse(addedUser, "A new user added"))
                .build();
    }

}
