package com.Mayuri_EV_Vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.dto.RegionDto;
import com.Mayuri_EV_Vehicle.entity.Region;
import com.Mayuri_EV_Vehicle.model.RegionTable;

public interface RegionRepository extends JpaRepository<RegionTable, String> {
	RegionTable findByRegionName(String regionName);

	RegionTable save(RegionTable regionTable);
}
