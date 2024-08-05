package com.Mayuri_EV_Vehicle.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.ChangePasswordDTO;
import com.Mayuri_EV_Vehicle.dto.CreateUserDTO;
import com.Mayuri_EV_Vehicle.dto.NavigationDTO;
import com.Mayuri_EV_Vehicle.dto.UserDTO;
import com.Mayuri_EV_Vehicle.dto.UserRoleDTO;
import com.Mayuri_EV_Vehicle.model.Authority;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.model.RegionTable;
import com.Mayuri_EV_Vehicle.model.RoleAuthorityMapping;
import com.Mayuri_EV_Vehicle.model.UserEntity;
import com.Mayuri_EV_Vehicle.model.UserLoginLog;
import com.Mayuri_EV_Vehicle.model.UserRole;
import com.Mayuri_EV_Vehicle.model.UserRoleMapping;
import com.Mayuri_EV_Vehicle.repository.RoleAuthorityMappingRepository;
import com.Mayuri_EV_Vehicle.repository.UserEntityRepository;
import com.Mayuri_EV_Vehicle.repository.UserLoginLogRepository;
import com.Mayuri_EV_Vehicle.repository.UserRoleMappingRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	

    private final UserEntityRepository userEntityRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final UserLoginLogRepository userLoginLogRepository;
    private final RoleAuthorityMappingRepository roleAuthorityMappingRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionService regionService;

    public UserServiceImpl(UserEntityRepository userEntityRepository,
                           UserRoleMappingRepository userRoleMappingRepository,
                           UserLoginLogRepository userLoginLogRepository,
                           RoleAuthorityMappingRepository roleAuthorityMappingRepository,
                           @Lazy PasswordEncoder passwordEncoder,RegionService regionService) {
        this.userEntityRepository = userEntityRepository;
        this.userRoleMappingRepository = userRoleMappingRepository;
        this.userLoginLogRepository = userLoginLogRepository;
        this.roleAuthorityMappingRepository = roleAuthorityMappingRepository;
        this.passwordEncoder = passwordEncoder;
        this.regionService = regionService;
    }

    @Override
    public UserEntity getUserEntity(String userCode) {
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(userCode);
        return optionalUserEntity.orElse(null);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    @Override
    public List<UserDTO> getAllUserDetails() {
        return getAllUsers().stream()
                .map(userEntity -> {
                    List<UserRole> userRoles = getUserRoles(userEntity);
                    List<UserRoleDTO> userRoleDTOS = userRoles.stream()
                            .map(r -> {
                                UserRoleDTO userRoleDTO = new UserRoleDTO();
                                userRoleDTO.setRoleCode(r.name());
                                userRoleDTO.setRoleName(r.getRoleName());
                                return userRoleDTO;
                            })
                            .collect(Collectors.toList());

                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserCode(userEntity.getUserCode());
                    userDTO.setFirstName(userEntity.getFirstName());
                    userDTO.setLastName(userEntity.getLastName());
                    userDTO.setEnabled(userEntity.isEnabled());
                    userDTO.setRoles(userRoleDTOS);

                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getUserRoles(UserEntity userEntity) {
        List<UserRoleMapping> allUserRoles = userRoleMappingRepository.findAllByUserEntity(userEntity);
        return allUserRoles.stream()
                .map(UserRoleMapping::getUserRole)
                .collect(Collectors.toList());
    }

    @Override
    public List<Authority> getUserAuthorities(List<UserRole> userRoles) {
        return roleAuthorityMappingRepository.findAllByUserRoleIn(userRoles)
                .stream()
                .map(RoleAuthorityMapping::getAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public List<NavigationDTO> getUserNavigation(List<GrantedAuthority> userAuthorities) {
        return userAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> !a.startsWith("ROLE_"))
                .map(Authority::valueOf)
                .filter(a -> !ObjectUtils.isEmpty(a.getUrl()))
                .sorted(Comparator.comparingInt(Authority::getDisplayOrder))
                .map(a -> new NavigationDTO(a.getName(), a.getUrl(), a.getIcon(), a.getActiveNav()))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public void createUser(CreateUserDTO createUser, DomainUser domainUser) {
        UserEntity createdBy = getUserEntity(domainUser.getUsername());
        String password = passwordEncoder.encode(createUser.getUserPass());
        System.out.println("password="+password);
        UserRole userRole = UserRole.valueOf(createUser.getUserRole());
        RegionTable regionTable = regionService.getRegionById(createUser.getRegionId());
      Region region = Region.REGION_1;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserCode(createUser.getUserCode());
        userEntity.setUserPass(password);
        userEntity.setFirstName(createUser.getFirstName());
        userEntity.setLastName(createUser.getLastName());
        userEntity.setEnabled(true);
       userEntity.setRegionTable(regionTable);
       userEntity.setRegion(region);
        

        userEntity = userEntityRepository.save(userEntity);

        UserRoleMapping userRoleMapping = new UserRoleMapping(userEntity, userRole, createdBy);
        userRoleMappingRepository.save(userRoleMapping);
    }

    @Override
    public void toggleUserStatus(String userCode) {
        UserEntity userEntity = getUserEntity(userCode);
        userEntity.setEnabled(!userEntity.isEnabled());
        userEntityRepository.save(userEntity);
    }

    @Override
    public List<UserRoleDTO> getAssignedRoles(String userCode) {
        UserEntity userEntity = getUserEntity(userCode);
        return userRoleMappingRepository.findAllByUserEntity(userEntity)
                .stream()
                .map(UserRoleMapping::getUserRole)
                .map(ur -> {
                    UserRoleDTO userRole = new UserRoleDTO();
                    userRole.setRoleCode(ur.name());
                    userRole.setRoleName(ur.getRoleName());
                    return userRole;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRoleDTO> getUnassignedRoles(String userCode) {
        List<UserRoleDTO> assignedRoles = getAssignedRoles(userCode);
        return Arrays.stream(UserRole.values())
                .filter(a -> assignedRoles.stream().noneMatch(ar -> a.name().equals(ar.getRoleCode())))
                .map(ur -> {
                    UserRoleDTO userRole = new UserRoleDTO();
                    userRole.setRoleCode(ur.name());
                    userRole.setRoleName(ur.getRoleName());
                    return userRole;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePassword, DomainUser domainUser) {
        return false;
    }

	@Override
	public void deleteUser(String userId) {
		UserEntity entity = getUserById(userId);
		
		List<UserLoginLog> userLoginLogs = userLoginLogRepository.findAllByUserEntity(entity);
		
		for(UserLoginLog list : userLoginLogs)
		{
			userLoginLogRepository.delete(list);
		}
		
		List<UserRoleMapping> userRoleMapping = userRoleMappingRepository.findAllByUserEntity(entity);
		
		for(UserRoleMapping form : userRoleMapping)
		{
			userRoleMappingRepository.delete(form);
		}
		
		userEntityRepository.delete(entity);
	}
	
	public UserEntity getUserById(String userId) {
		Optional<UserEntity> userDetails = userEntityRepository.findById(userId);
		if(!userDetails.isPresent()){
            throw new RuntimeException("No User with this id found");
        }
        return userDetails.get();
	}


}
