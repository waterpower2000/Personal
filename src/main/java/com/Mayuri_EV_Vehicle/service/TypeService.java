package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.TypeDto;
import com.Mayuri_EV_Vehicle.entity.Type;

import java.util.List;

public interface TypeService {
    TypeDto saveType(TypeDto typeDto);

    TypeDto getTypeById(String id);

    List<TypeDto> getAllType();
    Type getTypeByName(String name);
}
