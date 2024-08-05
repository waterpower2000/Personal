package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateUserManagmentDto;
import com.Mayuri_EV_Vehicle.dto.UserManagmentDto;

public interface UserManagmentService {
    UserManagmentDto createUser(CreateUserManagmentDto createUserManagmentDto);
}
