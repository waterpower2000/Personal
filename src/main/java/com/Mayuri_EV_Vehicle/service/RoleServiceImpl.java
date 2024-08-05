package com.Mayuri_EV_Vehicle.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.AssignRoleToUserDTO;
import com.Mayuri_EV_Vehicle.dto.PermissionDTO;
import com.Mayuri_EV_Vehicle.dto.RoleDTO;
import com.Mayuri_EV_Vehicle.dto.UserDTO;
import com.Mayuri_EV_Vehicle.model.Authority;
import com.Mayuri_EV_Vehicle.model.RoleAuthorityMapping;
import com.Mayuri_EV_Vehicle.model.UserEntity;
import com.Mayuri_EV_Vehicle.model.UserRole;
import com.Mayuri_EV_Vehicle.model.UserRoleMapping;
import com.Mayuri_EV_Vehicle.repository.RoleAuthorityMappingRepository;
import com.Mayuri_EV_Vehicle.repository.UserRoleMappingRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	private final UserService userService;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final RoleAuthorityMappingRepository roleAuthorityMappingRepository;

    public RoleServiceImpl(UserService userService, UserRoleMappingRepository userRoleMappingRepository,
                           RoleAuthorityMappingRepository roleAuthorityMappingRepository) {
        this.userService = userService;
        this.userRoleMappingRepository = userRoleMappingRepository;
        this.roleAuthorityMappingRepository = roleAuthorityMappingRepository;
    }

    @Override
    public List<RoleDTO> getAllRoleDetails() {
        Map<UserRole, Long> userRoleListMap = userRoleMappingRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(UserRoleMapping::getUserRole, Collectors.counting()));
        Map<UserRole, Long> roleAuthorityListMap = roleAuthorityMappingRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(RoleAuthorityMapping::getUserRole, Collectors.counting()));

        return Arrays.stream(UserRole.values())
                .map(r -> {
                    long totalUser = userRoleListMap != null && userRoleListMap.get(r) != null ? userRoleListMap.get(r) : 0;
                    long totalPermission = roleAuthorityListMap != null && roleAuthorityListMap.get(r) != null ? roleAuthorityListMap.get(r) : 0;

                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setRoleCode(r.name());
                    roleDTO.setRoleName(r.getRoleName());
                    roleDTO.setTotalUsers(totalUser);
                    roleDTO.setTotalPermission(totalPermission);
                    return roleDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUserForRoleAssignment(String roleCode) {
        List<UserDTO> allUserDetails = userService.getAllUserDetails();
        return allUserDetails.stream()
                .filter(u -> u.getRoles().stream()
                            .noneMatch(r -> r.getRoleCode().equals(roleCode)))
                .collect(Collectors.toList());
    }

    @Override
    public void assignRoleToUser(AssignRoleToUserDTO assignRoleToUser, DomainUser domainUser) {
        UserEntity userEntity = userService.getUserEntity(assignRoleToUser.getUserCode());
        UserEntity createdBy = userService.getUserEntity(domainUser.getUsername());
        UserRole userRole = UserRole.valueOf(assignRoleToUser.getRoleCode());

        UserRoleMapping userRoleMapping = new UserRoleMapping(userEntity, userRole, createdBy);
        userRoleMappingRepository.save(userRoleMapping);
    }

    @Override
    public void revokeRoleFromUser(String userCode, String roleCode) {
        UserEntity userEntity = userService.getUserEntity(userCode);
        UserRole userRole = UserRole.valueOf(roleCode);
        userRoleMappingRepository.deleteByUserEntityAndUserRole(userEntity, userRole);
    }

    @Override
    public List<PermissionDTO> getAssignedPermissions(String roleCode) {
        UserRole userRole = UserRole.valueOf(roleCode);
        List<RoleAuthorityMapping> permissionsForRole = roleAuthorityMappingRepository.findAllByUserRoleIn(Collections.singletonList(userRole));
        return permissionsForRole.stream()
                .map(RoleAuthorityMapping::getAuthority)
                .map(a -> new PermissionDTO(a.name(), a.getName(), a.getUrl() != null))
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> getUnassignedPermissions(String roleCode) {
        List<PermissionDTO> assignedPermissions = getAssignedPermissions(roleCode);
        return Arrays.stream(Authority.values())
                .filter(a -> assignedPermissions.stream().noneMatch(p -> a.name().equals(p.getPermissionCode())))
                .map(a -> new PermissionDTO(a.name(), a.getName(), a.getUrl() != null))
                .collect(Collectors.toList());
    }

    @Override
    public void grantPermission(String roleCode, String permissionCode, DomainUser domainUser) {
        UserRole userRole = UserRole.valueOf(roleCode);
        Authority authority = Authority.valueOf(permissionCode);
        UserEntity createdBy = userService.getUserEntity(domainUser.getUsername());
        roleAuthorityMappingRepository.save(new RoleAuthorityMapping(userRole, authority, createdBy));
    }

    @Override
    public void revokePermission(String roleCode, String permissionCode) {
        UserRole userRole = UserRole.valueOf(roleCode);
        Authority authority = Authority.valueOf(permissionCode);
        roleAuthorityMappingRepository.deleteByUserRoleAndAuthority(userRole, authority);
    }



}
