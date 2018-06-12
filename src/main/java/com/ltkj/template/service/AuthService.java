package com.ltkj.template.service;

import java.util.Map;

import com.ltkj.template.model.User;

public interface AuthService {
    User register(User userToAdd);
    Map<String, Object> login(String username, String password);
    String refresh(String oldToken);
}
