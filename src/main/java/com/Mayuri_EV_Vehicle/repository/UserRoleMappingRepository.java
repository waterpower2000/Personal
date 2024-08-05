package com.Mayuri_EV_Vehicle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.model.UserEntity;
import com.Mayuri_EV_Vehicle.model.UserRole;
import com.Mayuri_EV_Vehicle.model.UserRoleMapping;


public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, String> {
	
	List<UserRoleMapping> findAllByUserEntity(UserEntity userEntity);
    void deleteByUserEntityAndUserRole(UserEntity userEntity, UserRole userRole);
    
}
