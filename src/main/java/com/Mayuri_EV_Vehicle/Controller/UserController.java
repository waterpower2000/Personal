package com.Mayuri_EV_Vehicle.Controller;


import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.AssignRoleToUserDTO;
import com.Mayuri_EV_Vehicle.dto.CreateUserDTO;
import com.Mayuri_EV_Vehicle.dto.MessageDTO;
import com.Mayuri_EV_Vehicle.dto.RegionDto;
import com.Mayuri_EV_Vehicle.service.RegionService;
import com.Mayuri_EV_Vehicle.service.RoleService;
import com.Mayuri_EV_Vehicle.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.Mayuri_EV_Vehicle.model.Authority.A0001;

import java.util.List;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/app")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final RegionService regionService;

    public UserController(UserService userService, RoleService roleService, RegionService regionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.regionService= regionService;
    }

    @GetMapping("/users.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0001.name())")
    public ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("USERS", userService.getAllUserDetails());
        modelAndView.addObject("activeNav", A0001.getActiveNav());

        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("/user/add-users.html")
//    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0002.name())")
    public ModelAndView addUserPage(@AuthenticationPrincipal DomainUser domainUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ROLES", roleService.getAllRoleDetails());
        modelAndView.addObject("createUser", new CreateUserDTO());
        List<RegionDto> allRegion = regionService.getAllRegionDetail();
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.addObject("allRegion",allRegion);
        
        modelAndView.addObject("USERS", userService.getAllUserDetails());
        modelAndView.addObject("activeNav", A0001.getActiveNav());
        
        
        modelAndView.setViewName("user-management");
        return modelAndView;
    }

    @PostMapping("/user/create-user.html")
//    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0002.name())")
    public ModelAndView createUser(@ModelAttribute("createUser") CreateUserDTO createUser,
                                         @AuthenticationPrincipal DomainUser domainUser,HttpSession session) {
        userService.createUser(createUser, domainUser);
        ModelAndView modelAndView = new ModelAndView();
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        session.setAttribute("message", new MessageDTO("User Added Successfully", "Success"));
        modelAndView.addObject("message", "User Added Successfully");
        modelAndView.setViewName("redirect:/app/user/add-users.html");
        return modelAndView;
    }

    @PostMapping("/user/toggle-user-status.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0003.name())")
    public @ResponseBody String toggleUserStatus(@RequestParam String userCode) {
        userService.toggleUserStatus(userCode);
        return "User status changed";
    }

    @GetMapping("/user/manager-user-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0004.name())")
    public ModelAndView manageUserRole(@RequestParam String userCode) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("USERCODE", userCode);
        modelAndView.addObject("ASSIGNEDROLE", userService.getAssignedRoles(userCode));
        modelAndView.addObject("UNASSIGNEDROLE", userService.getUnassignedRoles(userCode));

        modelAndView.setViewName("users/manage-user-role");
        return modelAndView;
    }

    @PostMapping("/user/grant-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0004.name())")
    public ModelAndView grantRole(@RequestParam String userCode,
                                  @RequestParam String roleCode,
                                  @AuthenticationPrincipal DomainUser domainUser) {
        AssignRoleToUserDTO assignRoleToUser = new AssignRoleToUserDTO();
        assignRoleToUser.setUserCode(userCode);
        assignRoleToUser.setRoleCode(roleCode);

        roleService.assignRoleToUser(assignRoleToUser, domainUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("USERCODE", userCode);
        modelAndView.addObject("ASSIGNEDROLE", userService.getAssignedRoles(userCode));
        modelAndView.addObject("UNASSIGNEDROLE", userService.getUnassignedRoles(userCode));

        modelAndView.setViewName("users/manage-user-role");
        return modelAndView;
    }

    @PostMapping("/user/revoke-role.html")
    @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0004.name())")
    public ModelAndView revokeRole(@RequestParam String userCode,
                                  @RequestParam String roleCode) {
        roleService.revokeRoleFromUser(userCode, roleCode);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("USERCODE", userCode);
        modelAndView.addObject("ASSIGNEDROLE", userService.getAssignedRoles(userCode));
        modelAndView.addObject("UNASSIGNEDROLE", userService.getUnassignedRoles(userCode));

        modelAndView.setViewName("users/manage-user-role");
        return modelAndView;
    }
    
    
    @GetMapping("/user/delete/{id}")
    public ModelAndView deleteUser( @PathVariable String id, HttpSession session){
         userService.deleteUser(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ROLES", roleService.getAllRoleDetails());
        modelAndView.addObject("createUser", new CreateUserDTO());
        List<RegionDto> allRegion = regionService.getAllRegionDetail();
        modelAndView.addObject("allRegion",allRegion);
        
        modelAndView.addObject("USERS", userService.getAllUserDetails());
        modelAndView.addObject("activeNav", A0001.getActiveNav());
        session.setAttribute("message", new MessageDTO("User Successfully deleted", "Success"));
        modelAndView.addObject("message", "User Successfully deleted");
        modelAndView.setViewName("redirect:/app/user/add-users.html");

        return modelAndView;
    }
}
