package com.sinaukoding.foodordersystem.controller.app;

import com.sinaukoding.foodordersystem.config.UserLoggedInConfig;
import com.sinaukoding.foodordersystem.model.request.LoginRequestRecord;
import com.sinaukoding.foodordersystem.model.request.RegisterRequestRecord;
import com.sinaukoding.foodordersystem.model.response.BaseResponse;
import com.sinaukoding.foodordersystem.service.app.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody LoginRequestRecord request) {
        return BaseResponse.ok("berhasil login", authService.login(request));
    }

    @PostMapping("register")
    public BaseResponse<?> register(@RequestBody RegisterRequestRecord request) {
        return BaseResponse.ok("Registrasi berhasil", authService.register(request));
    }

    @GetMapping("logout")
    public BaseResponse<?> logout(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        var userLoggedIn = userLoggedInConfig.getUser();
        authService.logout(userLoggedIn);
        return BaseResponse.ok("Berhasil logout", null);
    }

}
