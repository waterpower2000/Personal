package com.Mayuri_EV_Vehicle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignRoleToUserDTO {
	
	private String roleCode;
    private String userCode;

    public AssignRoleToUserDTO(String roleCode) {
        this.roleCode = roleCode;
    }

}
