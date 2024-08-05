package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.Type;
import com.Mayuri_EV_Vehicle.entity.TypeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, String> {
    Type getByType(TypeList type);
}
