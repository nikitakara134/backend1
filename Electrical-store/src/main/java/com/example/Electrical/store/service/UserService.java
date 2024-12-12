package com.example.Electrical.store.service;

import java.util.List;

import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.User;




public interface UserService {

    void registerUser(User user) throws UserException;

    List<User> listAllUsers(String authenticationToken) throws UserException, CurrentUserServiceException;

    void updateUser(User user, String authenticationToken) throws UserException, CurrentUserServiceException;

    void deleteUser(String email, String authenticationToken) throws UserException, CurrentUserServiceException;

}