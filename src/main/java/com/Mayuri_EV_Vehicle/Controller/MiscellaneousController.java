package com.Mayuri_EV_Vehicle.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.service.MiscellaneousHeadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.service.MiscellaneousService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

@Controller
@RequestMapping("/api/miscellaneous")
public class MiscellaneousController {
	
	private MiscellaneousService miscellaneousService;
	private final TemplateEngine templateEngine;
	private MiscellaneousHeadsService miscellaneousHeadsService;
	@Autowired
    ServletContext servletContext;
	
	public MiscellaneousController(MiscellaneousService miscellaneousService,TemplateEngine templateEngine,MiscellaneousHeadsService miscellaneousHeadsService)
	{
		super();
		this.miscellaneousService= miscellaneousService;
		this.templateEngine = templateEngine;
		this.miscellaneousHeadsService=miscellaneousHeadsService;
	}
	
	
	@GetMapping("/get")
	public ModelAndView getMiscellaneousExpense(@AuthenticationPrincipal DomainUser domainUser)
	{
		ModelAndView modelAndView = new ModelAndView();
		List<MiscellaneousDto> list= miscellaneousService.getAllMiscSales(domainUser);
		String reg;
		reg=domainUser.getRegion_name();
		List<MiscellaneousHeadsDto> headslist=miscellaneousHeadsService.findallheads();
		modelAndView.addObject("reg", reg);
		modelAndView.addObject("createMiscellaneousDto", new CreateMiscellaneousDto());
		modelAndView.addObject("createMiscellaneousHeads", new CreateMiscellaneousHeads());
		modelAndView.addObject("miscList",list);
		modelAndView.addObject("headslist",headslist);
		modelAndView.setViewName("miscellaneous");
		return modelAndView;
	}
	
	@PostMapping("/save")
    public ModelAndView savePuchase(@ModelAttribute("createMiscellaneousDto") CreateMiscellaneousDto createMiscellaneousDto,@AuthenticationPrincipal DomainUser domainUser,HttpSession session){
        
		ModelAndView modelAndView = new ModelAndView();
		miscellaneousService.saveMiscellaneous(createMiscellaneousDto,domainUser);
		
		String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
		
		session.setAttribute("message", new MessageDTO("Entry added Successfully", "Success"));
        modelAndView.addObject("message", "Entry added Successfully");
		
		modelAndView.setViewName("redirect:/api/miscellaneous/get");
        return modelAndView;
    }
	
	@GetMapping("/delete/{id}")
    public ModelAndView deletePurchase(@PathVariable String id, HttpSession session){
		
		miscellaneousService.deleteMiscById(id);
		ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("message", new MessageDTO("Miscellaneous Entry Deleted Successfully", "Success"));
        modelAndView.addObject("message", "Miscellaneous Entry Deleted Successfully");
        modelAndView.setViewName("redirect:/api/miscellaneous/get");
        return modelAndView;
		
	}
	
	
	@RequestMapping(path = "/generate/pdf")
	public ResponseEntity<?> getmiscPDF(@AuthenticationPrincipal DomainUser domainUser,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String orderHtml = null;
		List<MiscellaneousDto> list= miscellaneousService.getAllMiscSales(domainUser);
		WebContext context = new WebContext(request, response, servletContext);
		
		context.setVariable("miscList", list);
		
		orderHtml = templateEngine.process("misc-table", context);
		
		ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css");
        converterProperties.setBaseUri("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js");
        converterProperties.setBaseUri("https://www.rawalco.com/img/mayuri.png");
        converterProperties.setBaseUri("https://www.rawalco.com/css/styleOne.css");
        converterProperties.setBaseUri("https://www.rawalco.com/assets/js/main.js");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/bootstrap/css/bootstrap.min.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/bootstrap-icons/bootstrap-icons.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/boxicons/css/boxicons.min.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/quill/quill.snow.csss");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/quill/quill.bubble.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/remixicon/remixicon.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/simple-datatables.js");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/vendor/bootstrap/js/bootstrap.bundle.min.js");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
        converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
        
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
        
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=miscallaneous_table.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
		
	}
	
	@GetMapping("/bulk/miscellaneous")
    public ResponseEntity<ByteArrayResource> downloadSalesData(@AuthenticationPrincipal DomainUser domainUser) {
        byte[] data = miscellaneousService.downloadSalesData(domainUser);
        ByteArrayResource resource = new ByteArrayResource(data);
        String dateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-disposition", "attachment; filename=\"Miscellaneous_"+dateTimeFormat+"\".xlsx")
                .body(resource);
    }
	
}
