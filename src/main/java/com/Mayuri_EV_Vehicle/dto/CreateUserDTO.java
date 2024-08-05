package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.model.RegionTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
	
	 private String userCode;
	    private String userPass;
	    private String firstName;
	    private String lastName;
	    private String userRole;
	    private String region;
		private String regionId;

}
