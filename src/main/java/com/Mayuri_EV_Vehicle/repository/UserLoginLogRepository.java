package com.Mayuri_EV_Vehicle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.model.UserEntity;
import com.Mayuri_EV_Vehicle.model.UserLoginLog;



public interface UserLoginLogRepository extends JpaRepository<UserLoginLog, String>{
	
	List<UserLoginLog> findAllByUserEntity(UserEntity userEntity);

}
