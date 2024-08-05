package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {
	
	private String id;	
	private String regionName;
	private String regionDetails;
}
