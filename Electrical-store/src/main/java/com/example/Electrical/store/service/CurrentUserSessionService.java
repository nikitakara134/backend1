package com.example.Electrical.store.service;


import com.example.Electrical.store.dto.MyResponseDto;
import com.example.Electrical.store.dto.UserDto;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.UserException;

public interface CurrentUserSessionService {

    MyResponseDto loginUser(UserDto userDto) throws UserException, CurrentUserServiceException;

    void logoutUser(String authenticationToken) throws UserException, CurrentUserServiceException;

    boolean isLoggedIn(String authenticationToken) throws CurrentUserServiceException;

    boolean isAdmin(String authenticationToken) throws CurrentUserServiceException;
}
