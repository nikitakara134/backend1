package com.example.Electrical.store.helper;

import com.example.Electrical.store.model.CurrentUserSession;
import com.example.Electrical.store.repository.CurrentUserSessionRepository;

public class Helper {

    public static boolean isLoggedIn(String authenticationToken, CurrentUserSessionRepository currentUserSessionRepository) {
        // Перевіряємо, чи існує активна сесія для токена
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByAuthenticationToken(authenticationToken);
        return currentUserSession != null;
    }

    public static boolean isAdmin(String authenticationToken, CurrentUserSessionRepository currentUserSessionRepository) {
        // Перевірка, чи є користувач адміністратором
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByAuthenticationToken(authenticationToken);
        return currentUserSession != null && currentUserSession.getRole().equals("admin");
    }
}
