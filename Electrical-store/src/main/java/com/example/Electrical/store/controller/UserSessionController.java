package com.example.Electrical.store.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Electrical.store.dto.MyResponseDto;
import com.example.Electrical.store.dto.UserDto;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.User;
import com.example.Electrical.store.service.CurrentUserSessionService;
import com.example.Electrical.store.service.UserService;



@CrossOrigin("http://localhost:3000/")
@RestController
public class UserSessionController {

    private final Logger logger = LoggerFactory.getLogger(UserSessionController.class);
    @Autowired
    private CurrentUserSessionService currentUserSessionService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<MyResponseDto> loginUser(@RequestBody UserDto userDto)
            throws UserException, CurrentUserServiceException {
        logger.info("called loginUser method of CurrentUserSessionService");
        MyResponseDto response = currentUserSessionService.loginUser(userDto);
        logger.info("User Login Successful");
        logger.info("Session Started");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }


    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam String authenticationToken)
            throws UserException, CurrentUserServiceException {

        logger.info("called logoutUser method of CurrentUserSessionService");
        currentUserSessionService.logoutUser(authenticationToken);
        logger.info("User Logged Out");
        logger.info("Session Ended");

        return new ResponseEntity<>("User Logged Out", HttpStatus.OK);

    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) throws UserException {

        userService.registerUser(user);

        logger.info("User Registered");

        return new ResponseEntity<>("User registered", HttpStatus.ACCEPTED);

    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> listAllUsers(@RequestParam String authenticationToken)
            throws UserException, CurrentUserServiceException {

        logger.info("called listAllUsers method of UserService");
        List<User> users = userService.listAllUsers(authenticationToken);
        logger.info("All user details fetched successfully");

        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }


    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestParam String authenticationToken, @RequestBody User user)
            throws UserException, CurrentUserServiceException {

        logger.info("called updateUser method of UserService");
        userService.updateUser(user, authenticationToken);
        logger.info("User Updated");

        return new ResponseEntity<>("User updated", HttpStatus.ACCEPTED);

    }


    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam String email, @RequestParam String authenticationToken)
            throws UserException, CurrentUserServiceException {

        logger.info("called deleteUser method of UserService");
        userService.deleteUser(email, authenticationToken);
        logger.warn("User Deleted");

        return new ResponseEntity<>("User deleted", HttpStatus.ACCEPTED);
    }

}