package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.AssignRoleToUserDTO;
import com.Mayuri_EV_Vehicle.dto.PermissionDTO;
import com.Mayuri_EV_Vehicle.dto.RoleDTO;
import com.Mayuri_EV_Vehicle.dto.UserDTO;

public interface RoleService {
	
	List<RoleDTO> getAllRoleDetails();
    List<UserDTO> getUserForRoleAssignment(String roleCode);
    void assignRoleToUser(AssignRoleToUserDTO assignRoleToUser, DomainUser domainUser);
    void revokeRoleFromUser(String userCode, String roleCode);
    List<PermissionDTO> getAssignedPermissions(String roleCode);
    List<PermissionDTO> getUnassignedPermissions(String roleCode);

    void grantPermission(String roleCode, String permissionCode, DomainUser domainUser);
    void revokePermission(String roleCode, String permissionCode);


}
