package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.TypeDto;
import com.Mayuri_EV_Vehicle.entity.Type;
import com.Mayuri_EV_Vehicle.entity.TypeList;
import com.Mayuri_EV_Vehicle.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public TypeDto saveType(TypeDto typeDto) {
        Type type= new Type();
        UUID randomUUID = UUID.randomUUID();
        type.setId(randomUUID.toString());
        type.setType(typeDto.getType());

        Type save = typeRepository.save(type);

        TypeDto typeDto1=new TypeDto();
        typeDto1.setId(save.getId());
        typeDto1.setType(save.getType());

        return typeDto1;
    }

    @Override
    public TypeDto getTypeById(String id) {
        Type type = typeRepository.findById(id).orElse(null);
        TypeDto typeDto = new TypeDto();
        typeDto.setId(type.getId());
        typeDto.setType(type.getType());
        return typeDto;
    }

    @Override
    public List<TypeDto> getAllType() {
        List<Type> all = typeRepository.findAll();
        return all.stream().map(type -> convertTypeDto(type)).collect(Collectors.toList());
    }

    @Override
    public Type getTypeByName(String name) {
        Type byType = typeRepository.getByType(TypeList.valueOf(name));
        return byType;
    }

    public TypeDto convertTypeDto(Type type){
        TypeDto typeDto=new TypeDto();
        typeDto.setId(type.getId());
        typeDto.setType(type.getType());
        return typeDto;
    }
}
