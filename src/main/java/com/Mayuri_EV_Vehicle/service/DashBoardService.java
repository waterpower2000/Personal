package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.DashBoard;

public interface DashBoardService {

	List<?> create(DashBoard dashBoard, DomainUser domainUser);

}
