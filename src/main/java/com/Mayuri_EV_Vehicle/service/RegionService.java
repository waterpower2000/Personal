package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.dto.CreateRegionDTO;
import com.Mayuri_EV_Vehicle.dto.RegionDto;
import com.Mayuri_EV_Vehicle.entity.Region;
import com.Mayuri_EV_Vehicle.model.RegionTable;




public interface RegionService {

	RegionDto create(CreateRegionDTO createRegionDTO);

	List<RegionTable> getAllRegion();

	RegionDto getRegionByIdDetails(String groupId);

	RegionTable getRegionById(String id);
	
	RegionDto updateRegion(String groupId, CreateRegionDTO createRegionDTO);
	
	 
	List<RegionDto> getAllRegionDetail();
	
	void deleteRegion(String id);
}

