package com.starterdemo.fetcher;

import com.starterdemo.model.User;
import com.starterdemo.persistence.UserDetailsDao;
import com.starterdemo.service.UserService;
import graphql.*;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class UsersListDataFetcher implements DataFetcher<DataFetcherResult<List<User>>> {

    private final UserService userService;

    @Inject
    public UsersListDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @Override
    public DataFetcherResult<List<User>> get(DataFetchingEnvironment environment) throws Exception {
        try
        {
            List<User> userList = userService.getAllUserDetails();

            return DataFetcherResult.<List<User>>newResult()
                    .data(userList)
                    .build();
        }
        catch (Exception e)
        {
            log.error("Exception occurred while fetching the user list via graphQL. Exception details:" + e.getMessage());
            e.printStackTrace();

            GraphQLError error = GraphqlErrorBuilder.newError().message("Unable to process the request. Please try again.")
                    .errorType(ErrorType.DataFetchingException)
                    .build();

            return DataFetcherResult.<List<User>>newResult()
                    .error(error)
                    .build();
        }

    }
}
