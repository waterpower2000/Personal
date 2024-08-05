package com.Mayuri_EV_Vehicle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.model.Authority;
import com.Mayuri_EV_Vehicle.model.RoleAuthorityMapping;
import com.Mayuri_EV_Vehicle.model.UserRole;


public interface RoleAuthorityMappingRepository extends JpaRepository<RoleAuthorityMapping, String>{
	List<RoleAuthorityMapping> findAllByUserRoleIn(List<UserRole> userRoles);
    void deleteByUserRoleAndAuthority(UserRole userRole, Authority authority);

}
