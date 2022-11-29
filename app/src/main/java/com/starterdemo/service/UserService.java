package com.starterdemo.service;

import com.starterdemo.enums.ExceptionCode;
import com.starterdemo.model.CustomException;
import com.starterdemo.model.User;
import com.starterdemo.persistence.UserDetailsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserDetailsDao userDetailsDao;

    @Inject
    public UserService(UserDetailsDao userDetailsDao)
    {
        this.userDetailsDao = userDetailsDao;
    }


    public List<User> getAllUserDetails()
    {
        return userDetailsDao.getAllUserDetails();
    }

    public User getUserDetails(String userId)
    {
        return userDetailsDao.getUserDetails(UUID.fromString(userId));
    }

    public Object updateUser(String userId, User updatedUser) throws Exception
    {
        try
        {
            User userFromDb = userDetailsDao.getUserDetails(UUID.fromString(userId));
            if (userFromDb == null){
                throw new CustomException(ExceptionCode.RESOURCE_NOT_FOUND);
            }
            userFromDb.updateUser(updatedUser);
            LOGGER.info("UserObj:" + userFromDb.getFirst_name());
            userDetailsDao.updateUserDetails(userFromDb);
            return Map.of("message", "User details updated", "user_details", userFromDb);
        }
        catch (Exception e){
            LOGGER.error("Exception occurred while updating the user data. UserId:" + userId + ", " +
                    "Exception Details:" + e.getMessage() + e.getStackTrace());
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Object deleteUser(String userId) throws Exception
    {
        try
        {
            User userFromDb = userDetailsDao.getUserDetails(UUID.fromString(userId));
            if (userFromDb == null){
                throw new CustomException(ExceptionCode.RESOURCE_NOT_FOUND);
            }
            LOGGER.info("Deleting user, UserId:" + userId);
            userDetailsDao.deleteUser(UUID.fromString(userId));
            return Map.of("message", "User removed.");
        }
        catch (Exception e){
            LOGGER.error("Exception occurred while deleting the user data. UserId:" + userId + ", " +
                    "Exception Details:" + e.getMessage() + e.getStackTrace());
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    public Object addNewUser(User user) throws Exception
    {
        try {
            user.setUser_id(UUID.randomUUID());
            userDetailsDao.addUserDetails(user);
            return Map.of("message", "A new user added.");
        }
        catch (Exception e){
            LOGGER.error("Exception occurred while storing a new user data." + e.getMessage() + e.getStackTrace());
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR, e);
        }
    }
}
