package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateUserManagmentDto;
import com.Mayuri_EV_Vehicle.dto.UserManagmentDto;
import com.Mayuri_EV_Vehicle.entity.Region;
import com.Mayuri_EV_Vehicle.entity.UserManagment;
import com.Mayuri_EV_Vehicle.model.RegionTable;
import com.Mayuri_EV_Vehicle.repository.UserManagmentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserManagmentServiceImpl implements UserManagmentService{
    private UserManagmentRepository userManagmentRepository;
    private RegionService regionService;

    public UserManagmentServiceImpl(UserManagmentRepository userManagmentRepository, RegionService regionService) {
        this.userManagmentRepository = userManagmentRepository;
        this.regionService = regionService;
    }

    @Override
    public UserManagmentDto createUser(CreateUserManagmentDto createUserManagmentDto) {
        RegionTable regionById = regionService.getRegionById(createUserManagmentDto.getRegion());
        UserManagment userManagment=new UserManagment( );
        UserManagment save = userManagmentRepository.save(userManagment);
        return convertToDto(save);
    }

    private UserManagmentDto convertToDto(UserManagment save) {
        UserManagmentDto userManagmentDto= new UserManagmentDto();
        userManagmentDto.setId(save.getId());
        userManagmentDto.setUserName(save.getUserName());
        userManagmentDto.setEmail(save.getEmail());
        userManagmentDto.setPassword(save.getPassword());
        userManagmentDto.setUserid(save.getUserid());
        userManagmentDto.setContactNumber(save.getContactNumber());
        return userManagmentDto;
    }
}
