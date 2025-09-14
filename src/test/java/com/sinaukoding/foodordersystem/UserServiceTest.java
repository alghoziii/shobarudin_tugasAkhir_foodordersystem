package com.sinaukoding.foodordersystem;

import com.sinaukoding.foodordersystem.model.enums.Role;
import com.sinaukoding.foodordersystem.model.enums.Status;
import com.sinaukoding.foodordersystem.model.request.UserRequestRecord;
import com.sinaukoding.foodordersystem.service.managementuser.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void addUserTest() {
        UserRequestRecord request = new UserRequestRecord(null,
                "and",
                "andi",
                "andi123@mail.com",
                "andi123",
                Status.AKTIF,
                Role.PEMBELI
        );
        userService.add(request);
    }

}
