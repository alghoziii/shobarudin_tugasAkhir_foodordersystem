package com.sinaukoding.foodordersystem.service.app.impl;

import com.sinaukoding.foodordersystem.entity.managementuser.User;
import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.enums.Role;
import com.sinaukoding.foodordersystem.model.enums.Status;
import com.sinaukoding.foodordersystem.model.request.LoginRequestRecord;
import com.sinaukoding.foodordersystem.model.request.RegisterRequestRecord;
import com.sinaukoding.foodordersystem.repository.managementuser.UserRepository;
import com.sinaukoding.foodordersystem.service.app.AuthService;
import com.sinaukoding.foodordersystem.service.app.ValidatorService;
import com.sinaukoding.foodordersystem.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ValidatorService validatorService;

    @Override
    public SimpleMap login(LoginRequestRecord request) {
        validatorService.validator(request);

        var user = userRepository.findByUsername(request.username().trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Username atau password salah"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Username atau password salah");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        user.setToken(token);
        user.setExpiredTokenAt(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        var result = new SimpleMap();
        result.put("token", token);
        return result;
    }

    @Override
    @Transactional
    public SimpleMap register(RegisterRequestRecord request) {
        validatorService.validator(request);

        final String username = request.username().trim().toLowerCase();
        final String email    = request.email().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) throw new RuntimeException("Username telah digunakan");
        if (userRepository.existsByEmailIgnoreCase(email)) throw new RuntimeException("Email sudah terdaftar");

        var user = new User();
        user.setNama(request.nama() != null && !request.nama().isBlank() ? request.nama().trim() : username); // ← penting
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(Status.AKTIF);
        user.setRole(Role.PEMBELI);

        userRepository.save(user);

        var result = new SimpleMap();
        result.put("userId", user.getId());
        return result;
    }

    @Override
    public void logout(User userLoggedIn) {
        userLoggedIn.setToken(null);
        userLoggedIn.setExpiredTokenAt(null);
        userRepository.save(userLoggedIn);
    }
}
