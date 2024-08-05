package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserManagmentDto {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String userid;
    private String contactNumber;
    private String region;
}
