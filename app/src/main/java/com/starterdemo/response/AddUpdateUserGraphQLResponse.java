package com.starterdemo.response;

import com.starterdemo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddUpdateUserGraphQLResponse {
    User user_details;
    String message;
}
