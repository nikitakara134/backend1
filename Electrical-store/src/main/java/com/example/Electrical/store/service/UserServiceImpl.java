package com.example.Electrical.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.helper.Helper;
import com.example.Electrical.store.model.CurrentUserSession;
import com.example.Electrical.store.model.User;
import com.example.Electrical.store.repository.CurrentUserSessionRepository;
import com.example.Electrical.store.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;


    @Override
    public void registerUser(User user) throws UserException {

        User existedUser = userRepository.findByEmail(user.getEmail());

        if (existedUser != null) {
            throw new UserException("User already exists with given email :" + user.getEmail());
        }

        userRepository.save(user);


    }

    @Override
    public List<User> listAllUsers(String authenticationToken) throws UserException, CurrentUserServiceException {

        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("login required");
        }

        if (!Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {
            throw new UserException("You are not allowed to perform this action");
        }

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserException("No users exists");
        }

        return users;

    }


    @Override
    public void updateUser(User user, String authenticationToken) throws UserException, CurrentUserServiceException {

        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("Login required");
        }

        if (Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            User existedUser = userRepository.findByEmail(user.getEmail());

            if (existedUser == null) {
                throw new UserException("User does not exists with given email : " + user.getEmail());
            }

            userRepository.save(user);

        } else {

            CurrentUserSession currentUserSession = currentUserSessionRepository.findByEmail(user.getEmail());

            if (!currentUserSession.getAuthenticationToken().equals(authenticationToken)) {
                throw new CurrentUserServiceException("Check user email id");
            }

            userRepository.save(user);

        }

    }

    @Override
    public void deleteUser(String email, String authenticationToken) throws UserException, CurrentUserServiceException {

        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("Login required");
        }

        if (Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            User existedUser = userRepository.findByEmail(email);

            if (existedUser == null) {
                throw new UserException("User does not exists with given email : " + email);
            }

            userRepository.delete(existedUser);
            CurrentUserSession currentUserSession = currentUserSessionRepository.findByEmail(email);

            currentUserSessionRepository.delete(currentUserSession);

        } else {

            CurrentUserSession currentUserSession = currentUserSessionRepository.findByEmail(email);

            if (!currentUserSession.getAuthenticationToken().equals(authenticationToken)) {
                throw new CurrentUserServiceException("Check user email id");
            }

            userRepository.delete(userRepository.findByEmail(email));

            currentUserSessionRepository.delete(currentUserSession);

        }

    }

}
