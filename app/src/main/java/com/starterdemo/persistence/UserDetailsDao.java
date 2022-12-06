package com.starterdemo.persistence;

import com.starterdemo.model.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;
import java.util.UUID;

public interface UserDetailsDao {

    @SqlQuery("select * from user_details")
    @RegisterBeanMapper(User.class)
    List<User> getAllUserDetails();

    @SqlQuery("select * from user_details where user_id = :user_id")
    @RegisterBeanMapper(User.class)
    User getUserDetails(@Bind("user_id") UUID userId);

    @SqlUpdate("insert into user_details(user_id, first_name, last_name, email_id) " +
            "values (:user.user_id, :user.first_name, :user.last_name, :user.email_id)")
    @GetGeneratedKeys
    @RegisterBeanMapper(User.class)
    User addUserDetails(@BindBean("user") User user);

    @SqlUpdate("update user_details set " +
            "first_name = :user.first_name, " +
            "last_name = :user.last_name, " +
            "email_id = :user.email_id " +
            "where user_id = :user.user_id")
    @GetGeneratedKeys
    @RegisterBeanMapper(User.class)
    User updateUserDetails(@BindBean("user") User user);

    @SqlUpdate("Delete from user_details where user_id = :user_id")
    void deleteUser(@Bind("user_id") UUID user_id);
}
