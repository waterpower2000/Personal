package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.AssignRoleToUserDTO;
import com.Mayuri_EV_Vehicle.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.Mayuri_EV_Vehicle.model.Authority.A0005;


@Controller
@RequestMapping("/app")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0005.name())")
    public ModelAndView getRoles() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ROLES", roleService.getAllRoleDetails());
        modelAndView.addObject("activeNav", A0005.getActiveNav());

        modelAndView.setViewName("roles/roles");
        return modelAndView;
    }

    @GetMapping("/role/assign-role-to-user.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0004.name())")
    public ModelAndView getAssignRoleToUserPage(@RequestParam String roleCode) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("AVAILABLEUSER", roleService.getUserForRoleAssignment(roleCode));
        modelAndView.addObject("createObject", new AssignRoleToUserDTO(roleCode));

        modelAndView.setViewName("roles/assign-role-to-user");
        return modelAndView;
    }

    @PostMapping("/role/add-user-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0004.name())")
    public ModelAndView assignRoleToUser(@ModelAttribute("createObject") AssignRoleToUserDTO assignRoleToUser,
                                            @AuthenticationPrincipal DomainUser domainUser) {
        roleService.assignRoleToUser(assignRoleToUser, domainUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/app/roles.html");
        return modelAndView;
    }

    @GetMapping("/role/assign-permission-to-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0006.name())")
    public ModelAndView getAssignPermissionToRolePage(@RequestParam String roleCode) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ROLECODE", roleCode);
        modelAndView.addObject("ASSIGNEDPERMISSION", roleService.getAssignedPermissions(roleCode));
        modelAndView.addObject("UNASSIGNEDPERMISSION", roleService.getUnassignedPermissions(roleCode));

        modelAndView.setViewName("roles/assign-permission-to-role");
        return modelAndView;
    }

    @PostMapping("/role/grant-permission-to-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0006.name())")
    public ModelAndView grantPermissionToRole(@RequestParam String roleCode, @RequestParam String permissionCode,
                                              @AuthenticationPrincipal DomainUser domainUser) {
        roleService.grantPermission(roleCode, permissionCode, domainUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ROLECODE", roleCode);
        modelAndView.addObject("ASSIGNEDPERMISSION", roleService.getAssignedPermissions(roleCode));
        modelAndView.addObject("UNASSIGNEDPERMISSION", roleService.getUnassignedPermissions(roleCode));

        modelAndView.setViewName("roles/assign-permission-to-role");
        return modelAndView;
    }

    @PostMapping("/role/revoke-permission-from-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0006.name())")
    public ModelAndView revokePermissionFromRole(@RequestParam String roleCode, @RequestParam String permissionCode) {
        roleService.revokePermission(roleCode, permissionCode);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ROLECODE", roleCode);
        modelAndView.addObject("ASSIGNEDPERMISSION", roleService.getAssignedPermissions(roleCode));
        modelAndView.addObject("UNASSIGNEDPERMISSION", roleService.getUnassignedPermissions(roleCode));

        modelAndView.setViewName("roles/assign-permission-to-role");
        return modelAndView;
    }
}
