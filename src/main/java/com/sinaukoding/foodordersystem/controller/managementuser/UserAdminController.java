package com.sinaukoding.foodordersystem.controller.managementuser;

import com.sinaukoding.foodordersystem.model.app.SimpleMap;
import com.sinaukoding.foodordersystem.model.enums.Role;
import com.sinaukoding.foodordersystem.model.response.BaseResponse;
import com.sinaukoding.foodordersystem.repository.managementuser.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin User Management API")
public class UserAdminController {

    private final UserRepository userRepository;

    @PutMapping("{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> updateRole(@PathVariable String id, @RequestParam Role role) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        if (role == Role.ADMIN) {
            throw new RuntimeException("Set role ADMIN hanya via seeding/manual.");
        }

        var oldRole = user.getRole();
        user.setRole(role);
        userRepository.save(user);

        var data = SimpleMap.createMap()
                .add("userId", user.getId())
                .add("username", user.getUsername())
                .add("oldRole", oldRole)
                .add("newRole", role);

        return BaseResponse.ok("Role berhasil diubah", data);
    }
}
