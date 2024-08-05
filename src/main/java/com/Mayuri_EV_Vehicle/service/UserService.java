package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.ChangePasswordDTO;
import com.Mayuri_EV_Vehicle.dto.CreateUserDTO;
import com.Mayuri_EV_Vehicle.dto.NavigationDTO;
import com.Mayuri_EV_Vehicle.dto.UserDTO;
import com.Mayuri_EV_Vehicle.dto.UserRoleDTO;
import com.Mayuri_EV_Vehicle.model.Authority;
import com.Mayuri_EV_Vehicle.model.UserEntity;
import com.Mayuri_EV_Vehicle.model.UserRole;



public interface UserService {
	
	UserEntity getUserEntity(String userCode);
    List<UserEntity> getAllUsers();
    List<UserDTO> getAllUserDetails();
    List<UserRole> getUserRoles(UserEntity userEntity);
    List<Authority> getUserAuthorities(List<UserRole> userRoles);
    List<NavigationDTO> getUserNavigation(List<GrantedAuthority> userAuthorities);
    void createUser(CreateUserDTO createUser, DomainUser domainUser);
    void toggleUserStatus(String userCode);
    List<UserRoleDTO> getAssignedRoles(String userCode);
    List<UserRoleDTO> getUnassignedRoles(String userCode);
    boolean changePassword(ChangePasswordDTO changePassword, DomainUser domainUser);
    
    void deleteUser(String userId);

}
