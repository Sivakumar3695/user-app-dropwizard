package com.starterdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class User {
        UUID user_id;
        String first_name;
        String last_name;
        String email_id;

        public void updateUser(User updatedUser)
        {
                if (updatedUser.getFirst_name() != null) first_name = updatedUser.getFirst_name();
                if (updatedUser.getLast_name() != null) last_name = updatedUser.getLast_name();
                if (updatedUser.getEmail_id() != null) email_id = updatedUser.getEmail_id();
        }
}
