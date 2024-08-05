package com.Mayuri_EV_Vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.model.UserEntity;


public interface UserEntityRepository extends JpaRepository<UserEntity, String>  {

}
