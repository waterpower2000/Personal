package com.Mayuri_EV_Vehicle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
	
	private String roleCode;
    private String roleName;
    private long totalPermission;
    private long totalUsers;

}
