package com.Mayuri_EV_Vehicle.service;

import javax.servlet.http.HttpServletRequest;

import com.Mayuri_EV_Vehicle.config.DomainUser;



public interface AuthenticationService {
	DomainUser authenticate(String username, String password, HttpServletRequest request);

}
