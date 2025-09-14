package com.sinaukoding.foodordersystem.service.app;

import com.sinaukoding.foodordersystem.entity.managementuser.User;
import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.request.LoginRequestRecord;
import com.sinaukoding.foodordersystem.model.request.RegisterRequestRecord;

public interface AuthService {

    SimpleMap login(LoginRequestRecord request);

    SimpleMap register(RegisterRequestRecord request);

    void logout(User userLoggedIn);

}
