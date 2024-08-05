package com.Mayuri_EV_Vehicle.model;

public enum UserRole {
	
	ROLE_ADMIN("Administrator"),
    ROLE_USER("User");
    
	
	private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

}
