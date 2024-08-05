package com.Mayuri_EV_Vehicle.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateRegionDTO;
import com.Mayuri_EV_Vehicle.dto.CreateUserDTO;
import com.Mayuri_EV_Vehicle.dto.MessageDTO;
import com.Mayuri_EV_Vehicle.dto.RegionDto;
import com.Mayuri_EV_Vehicle.entity.Region;
import com.Mayuri_EV_Vehicle.model.RegionTable;
import com.Mayuri_EV_Vehicle.service.RegionService;



@Controller
@RequestMapping("/api/region")
public class RegionController {
	
	private RegionService regionService;

	public RegionController(RegionService regionService) {
		this.regionService = regionService;
	}
	
	
	@GetMapping("/add")
//  @PreAuthorize("hasAuthority(T(com.sysbean.app.model.Authority).A0002.name())")
  public ModelAndView addRegionPage(@AuthenticationPrincipal DomainUser domainUser) {
      ModelAndView modelAndView = new ModelAndView();     
      modelAndView.addObject("createRegion", new CreateRegionDTO());
      List<RegionDto> allRegion = regionService.getAllRegionDetail();
       String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
      modelAndView.addObject("allRegion",allRegion);
      modelAndView.setViewName("region-management");
      return modelAndView;
  }
	
	@PostMapping("/save")
	public ModelAndView createRegion(@ModelAttribute("createRegion") CreateRegionDTO createRegionDTO,HttpSession session){
		ModelAndView modelAndView=new ModelAndView();
		session.setAttribute("message", new MessageDTO("Region Created Successfully", "Success"));
	      modelAndView.addObject("message", "Region Created Successfully");
        int a=1,b=2,c=3;
        RestTemplate restTemplate=new RestTemplate();
        //restTemplate.getForObject("https://bhashsms.com/api/sendmsg.php?user=Sysbeams&pass=123456&sender=BUZWAP&phone=8017418602&text=admission_campusive&priority=wa&stype=normal&Params="+a+","+b, String.class);

        RegionDto create = regionService.create(createRegionDTO);
		modelAndView.setViewName("redirect:/api/region/add");
		return modelAndView;
	}
	
	@GetMapping("/edit/{id}")
    public ModelAndView editRegion(@PathVariable String id){

		RegionDto region = regionService.getRegionByIdDetails(id);
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("region",region);
        //modelAndView.addObject("students", new CreateStudentDTO());
        modelAndView.setViewName("edit-region");

        return modelAndView;
    }
	
	@GetMapping("/delete/{id}")
    public ModelAndView deleteRegion(@PathVariable String id, HttpSession session){

		regionService.deleteRegion(id);
        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("message", new MessageDTO("Region Deleted Successfully", "Success"));
        modelAndView.addObject("message", "Region Deleted Successfully");
        modelAndView.setViewName("redirect:/api/region/add");
        return modelAndView;
    }
	
	@PostMapping("/update/{id}")
    public ModelAndView updateRegion(@ModelAttribute("region") CreateRegionDTO createRegionDTO, @PathVariable String id, HttpSession session){

        System.out.println(id);
        RegionDto regionDto = regionService.updateRegion(id, createRegionDTO);
        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("message", new MessageDTO("Region Updated Successfully", "Success"));
        modelAndView.addObject("message", "Region Successfully Updated");
        modelAndView.setViewName("redirect:/api/region/add");

        return modelAndView;
    }
	
//	@GetMapping
//	public ModelAndView getAll(){
//		ModelAndView modelAndView=new ModelAndView();
//		List<RegionDto> allRegion = regionService.getAllRegion();
//		return modelAndView;
//	}
//	@GetMapping("/{id}")
//	public ModelAndView getById(@PathVariable String id){
//		ModelAndView modelAndView=new ModelAndView();
//		RegionDto region = regionService.getRegion(id);
//		return modelAndView;
//	}
	
}
