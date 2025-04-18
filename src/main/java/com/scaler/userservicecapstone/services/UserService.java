package com.scaler.userservicecapstone.services;

import com.scaler.userservicecapstone.exceptions.UserLoginException;
import com.scaler.userservicecapstone.models.Token;
import com.scaler.userservicecapstone.models.User;

import java.util.List;

public interface UserService {
    User createUser(String email, String password, List<String> roles);
    Token login(String email, String password) throws UserLoginException;
    Boolean validateToken(String token);
}
