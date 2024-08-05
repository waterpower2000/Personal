package com.Mayuri_EV_Vehicle.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
	
	private String userCode;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private List<UserRoleDTO> roles;

}
