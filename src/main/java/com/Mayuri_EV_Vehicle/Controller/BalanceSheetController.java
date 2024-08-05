package com.Mayuri_EV_Vehicle.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.BalanceSheetDTO;
import com.Mayuri_EV_Vehicle.dto.CreateBalanceDTO;
import com.Mayuri_EV_Vehicle.dto.MiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.PurchaseDto;
import com.Mayuri_EV_Vehicle.dto.SalesDto;
import com.Mayuri_EV_Vehicle.service.BalanceSheetService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Controller
@RequestMapping("/api/balance")
public class BalanceSheetController {
	
	final private BalanceSheetService balanceSheetService;

	@Autowired
	ServletContext servletContext;
	private final TemplateEngine templateEngine;
	public BalanceSheetController(BalanceSheetService balanceSheetService,TemplateEngine templateEngine) {
		this.balanceSheetService = balanceSheetService;
		this.templateEngine = templateEngine;
	}
	
	
	@GetMapping("/get")
	public ModelAndView getMiscellaneousExpense(@AuthenticationPrincipal DomainUser domainUser)
	{
		ModelAndView modelAndView = new ModelAndView();
		//List<MiscellaneousDto> list= miscellaneousService.getAllMiscSales(domainUser);
		String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
		modelAndView.addObject("createBalanceDto", new CreateBalanceDTO());
		//modelAndView.addObject("miscList",list);
		modelAndView.setViewName("balance-sheet");
		return modelAndView;
	}
	
	@PostMapping("/sheet")
    public ModelAndView savePuchase(@ModelAttribute("createBalanceDto") CreateBalanceDTO createBalanceDTO,@AuthenticationPrincipal DomainUser domainUser,HttpSession session){

		ModelAndView modelAndView = new ModelAndView();
		//miscellaneousService.saveMiscellaneous(createMiscellaneousDto,domainUser);
		List<PurchaseDto> list=balanceSheetService.getPurchase(createBalanceDTO, domainUser);
		List<SalesDto> list2=balanceSheetService.getSale(createBalanceDTO, domainUser);
		List<MiscellaneousDto> list3 = balanceSheetService.getRTOMisc(createBalanceDTO, domainUser);
		List<MiscellaneousDto> list4 = balanceSheetService.getRestMisc(createBalanceDTO, domainUser);
		List<MiscellaneousDto> list5 = balanceSheetService.getCreditMisc(createBalanceDTO, domainUser);
		List<PurchaseDto> list8=balanceSheetService.openingStock(createBalanceDTO, domainUser);
		List<PurchaseDto> list9=balanceSheetService.closingStock(createBalanceDTO, domainUser);
		BalanceSheetDTO balanceSheetDTO = balanceSheetService.getBalanceAmount(createBalanceDTO, domainUser);
		double sumTotal = balanceSheetService.profit_loss(list, list2,list3);
		double totalSum = balanceSheetService.totalProfitLoss(sumTotal, list4, list5);
		System.out.println(sumTotal);
		String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
		modelAndView.addObject("sumTotal",sumTotal);
		modelAndView.addObject("totalSum",totalSum);
		modelAndView.addObject("list",list);
		modelAndView.addObject("list2",list2);
		modelAndView.addObject("list3",list3);
		modelAndView.addObject("list4",list4);
		modelAndView.addObject("list5",list5);
		modelAndView.addObject("list8",list8);
		modelAndView.addObject("list9",list9);
		modelAndView.addObject("CreateBalanceDTO",createBalanceDTO);
		modelAndView.addObject("balanceSheetDTO",balanceSheetDTO);
        modelAndView.addObject("message", "Entry added Successfully");
		modelAndView.setViewName("balance-sheet-table");
        return modelAndView;
    }

//	@RequestMapping(path = "/getBalanceSheet/pdf/{CreateBalanceDTO}/{balanceSheetDTO}")
//	public ResponseEntity<?> getBalanceSheetpdf(@AuthenticationPrincipal DomainUser domainUser, @PathVariable CreateBalanceDTO CreateBalanceDTO, @PathVariable BalanceSheetDTO balanceSheetDTO, HttpServletRequest request, HttpServletResponse response)throws IOException {
//		String orderHtml = null;
//		String reg;
//		reg=domainUser.getRegion_name();
//		WebContext context = new WebContext(request, response, servletContext);
//		context.setVariable("CreateBalanceDTO",CreateBalanceDTO);
//		context.setVariable("balanceSheetDTO",balanceSheetDTO);
//		context.setVariable("reg",reg);
//		orderHtml = templateEngine.process("balence_sheet_pdf", context);
//		ByteArrayOutputStream target = new ByteArrayOutputStream();
//		ConverterProperties converterProperties = new ConverterProperties();
//		converterProperties.setBaseUri("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css");
//		converterProperties.setBaseUri("https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js");
//		converterProperties.setBaseUri("https://www.rawalco.com/img/mayuri.png");
//		converterProperties.setBaseUri("https://www.rawalco.com/css/styleOne.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/assets/js/main.js");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/bootstrap/css/bootstrap.min.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/bootstrap-icons/bootstrap-icons.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/boxicons/css/boxicons.min.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/quill/quill.snow.csss");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/quill/quill.bubble.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/remixicon/remixicon.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/simple-datatables.js");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/vendor/bootstrap/js/bootstrap.bundle.min.js");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
//		converterProperties.setBaseUri("https://www.rawalco.com/vendor/simple-datatables/style.css");
//
//		HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
//
//		byte[] bytes = target.toByteArray();
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Balance_Sheet.pdf")
//				.contentType(MediaType.APPLICATION_PDF)
//				.body(bytes);
//	}
@RequestMapping(path = "/getBalanceSheet/pdf")
public ResponseEntity<?> getincomePDF(@RequestParam String from,@RequestParam String to,@AuthenticationPrincipal DomainUser domainUser,HttpServletRequest request, HttpServletResponse response) throws IOException {

	CreateBalanceDTO createBalanceDTO = new CreateBalanceDTO();
	createBalanceDTO.setDateFrom(from);
	createBalanceDTO.setDateTo(to);
	String reg;
	reg=domainUser.getRegion_name();
	String orderHtml = null;
	BalanceSheetDTO balanceSheetDTO = balanceSheetService.getBalanceAmount(createBalanceDTO, domainUser);
	WebContext context = new WebContext(request, response, servletContext);

	context.setVariable("CreateBalanceDTO", createBalanceDTO);
	context.setVariable("balanceSheetDTO", balanceSheetDTO);
	context.setVariable("reg",reg);
	orderHtml = templateEngine.process("balence_sheet_pdf", context);

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
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Balance_Sheet.pdf")
			.contentType(MediaType.APPLICATION_PDF)
			.body(bytes);

}
}
