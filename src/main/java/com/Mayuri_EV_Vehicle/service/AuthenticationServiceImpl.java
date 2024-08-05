package com.Mayuri_EV_Vehicle.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.model.Authority;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.model.UserEntity;
import com.Mayuri_EV_Vehicle.model.UserLoginLog;
import com.Mayuri_EV_Vehicle.repository.*;
import com.Mayuri_EV_Vehicle.model.UserRole;


@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserLoginLogRepository loginLogRepository;

    public AuthenticationServiceImpl(UserService userService,
                                     @Lazy PasswordEncoder passwordEncoder,
                                     UserLoginLogRepository loginLogRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.loginLogRepository = loginLogRepository;
    }

    @Override
    public DomainUser authenticate(String username, String password, HttpServletRequest request) {
        UserEntity userEntity = userService.getUserEntity(username);

        if(userEntity == null) {
            throw new BadCredentialsException("Invalid User.");
        }

        if(!userEntity.isEnabled()) {
            throw new BadCredentialsException("Login Disabled");
        }

        boolean matches = passwordEncoder.matches(password, userEntity.getUserPass());

        if(!matches) {
            throw new BadCredentialsException("Login Failed");
        }

        String userAgent = request.getHeader("User-Agent");
        String terminal = request.getRemoteAddr();

        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserEntity(userEntity);
        userLoginLog.setLoginTime(LocalDateTime.now());
        userLoginLog.setLoginTerminal(terminal);
        userLoginLog.setUserAgent(userAgent);
        userLoginLog = loginLogRepository.saveAndFlush(userLoginLog);

       // List<String> userRoleAndAuthorities = getUserRoleAndAuthorities(userEntity);

        DomainUser domainUser = new DomainUser(userEntity.getUserCode(), userEntity.getUserPass(),
                userEntity.isEnabled(), Collections.singletonList("ROLE_ADMIN"));

        domainUser.setFirstName(userEntity.getFirstName());
        domainUser.setLastName(userEntity.getLastName());
        domainUser.setLoginId(userLoginLog.getLoginId());
        domainUser.setLoginTime(userLoginLog.getLoginTime());
        domainUser.setRegion(userEntity.getRegion().toString());
        domainUser.setRegion_name(userEntity.getRegionTable().getRegionName());
      //  domainUser.setRegion(userEntity.getRegionTable().getRegionName());
        return domainUser;
    }

    public List<String> getUserRoleAndAuthorities(UserEntity userEntity) {
        List<UserRole> userRoles = userService.getUserRoles(userEntity);
        if(userRoles.isEmpty()) {
            throw new BadCredentialsException("No role is associated");
        }

        List<Authority> userAuthorities = userService.getUserAuthorities(userRoles);

        List<String> userRoleAuthorities = userRoles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        List<String> authorities = userAuthorities.stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        userRoleAuthorities.addAll(authorities);
        return userRoleAuthorities;
    }

}
