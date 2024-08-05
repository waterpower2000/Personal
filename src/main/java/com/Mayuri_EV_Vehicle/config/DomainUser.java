package com.Mayuri_EV_Vehicle.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;

import com.Mayuri_EV_Vehicle.model.Region;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DomainUser extends User {
	
	 private String firstName;
	    private String lastName;
	    private String loginId;
	    private LocalDateTime loginTime;
	    private LocalDateTime logoutTime;
	    private String region;
	    private String region_name;

	    public DomainUser(String username, String password, boolean enabled, List<String> authorities) {
	    	 super(username, password, enabled, true, true, true,
	                 authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	    }

}
