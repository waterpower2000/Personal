package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NavigationDTO {
	private String name;
    private String url;
    private String icon;
    private String activeNav;

}
