package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManagmentDto {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String userid;
    private String contactNumber;
    private String region;
}
