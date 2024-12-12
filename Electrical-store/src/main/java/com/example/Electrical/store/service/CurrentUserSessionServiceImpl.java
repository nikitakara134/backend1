package com.example.Electrical.store.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Electrical.store.dto.MyResponseDto;
import com.example.Electrical.store.dto.UserDto;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.CurrentUserSession;
import com.example.Electrical.store.model.User;
import com.example.Electrical.store.repository.CurrentUserSessionRepository;
import com.example.Electrical.store.repository.UserRepository;
import net.bytebuddy.utility.RandomString;



@Service
public class CurrentUserSessionServiceImpl implements CurrentUserSessionService {

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public MyResponseDto loginUser(UserDto userDto) throws UserException, CurrentUserServiceException {

        User user = userRepository.findByEmail(userDto.getEmail());

        if (user == null) {
            throw new UserException("User does not exists with given email ");
        }

        if (!user.getPassword().equals(userDto.getPassword())) {

            throw new UserException("Enter valid email or password");
        }

        CurrentUserSession currentUserSession = currentUserSessionRepository.findByEmail(userDto.getEmail());

        if (currentUserSession != null) {
            throw new CurrentUserServiceException("User already logged in");
        }

        CurrentUserSession newUserSession = new CurrentUserSession(user.getId(), LocalDateTime.now(),
                RandomString.make(12), user.getRole(), user.getEmail());

        String token = newUserSession.getAuthenticationToken();

        currentUserSessionRepository.save(newUserSession);

        MyResponseDto response = new MyResponseDto();
        response.setMessage("Log in Successful");
        response.setAuthenticationToken(token);
        response.setAuthorized(user.getRole().equals("admin"));
        return response;

    }


    @Override
    public void logoutUser(String authenticationToken) throws UserException, CurrentUserServiceException {

        if (!isLoggedIn(authenticationToken)) {
            throw new CurrentUserServiceException("User not logged in");
        }

        CurrentUserSession currentUserSession = currentUserSessionRepository
                .findByAuthenticationToken(authenticationToken);

        currentUserSessionRepository.delete(currentUserSession);


    }


    @Override
    public boolean isLoggedIn(String authenticationToken) {

        CurrentUserSession currentUserSession = currentUserSessionRepository
                .findByAuthenticationToken(authenticationToken);

        return currentUserSession != null;

    }

    @Override
    public boolean isAdmin(String authenticationToken) throws CurrentUserServiceException {

        CurrentUserSession currentUserSession = currentUserSessionRepository
                .findByAuthenticationToken(authenticationToken);

        return currentUserSession.getRole().equals("admin");
    }

}
