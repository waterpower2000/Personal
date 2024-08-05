package com.Mayuri_EV_Vehicle.Controller;


import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.*;
import com.Mayuri_EV_Vehicle.service.*;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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
@Controller
@RequestMapping("/api/sales")
public class SalesController {
    private SalesService salesService;
    private PurchaseService purchaseService;
    private EAutoVariantDetailsService eAutoVariantDetailsService;
    private SparePartsVariantDetailsService sparePartsVariantDetailsService;
    private MainLedgerService mainLedgerService;
    private MiscellaneousService miscellaneousService;

    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    
    public SalesController(SalesService salesService, PurchaseService purchaseService,MiscellaneousService miscellaneousService,
                           MainLedgerService mainLedgerService, EAutoVariantDetailsService eAutoVariantDetailsService, SparePartsVariantDetailsService sparePartsVariantDetailsService, TemplateEngine templateEngine) {
		super();
		this.salesService = salesService;
		this.purchaseService = purchaseService;
		this.miscellaneousService = miscellaneousService;
		this.mainLedgerService = mainLedgerService;
		this.eAutoVariantDetailsService=eAutoVariantDetailsService;
        this.sparePartsVariantDetailsService = sparePartsVariantDetailsService;
        this.templateEngine = templateEngine;
    }
    
//	@PostMapping
//    public ResponseEntity<SalesDto> savePurchase(@RequestBody CreateSalesDto createSalesDto){
//        SalesDto salesDto = salesService.saveOneSales(createSalesDto);
//        return  new ResponseEntity<>(salesDto, HttpStatus.CREATED);
//    }
    @PostMapping("/save")
    public ModelAndView savePuchase(@ModelAttribute("createSalesDto") CreateSalesDto createSalesDto,@AuthenticationPrincipal DomainUser domainUser,HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        SalesDto saveOneSales = salesService.saveOneSales(createSalesDto,domainUser);
        //eAutoVariantDetailsService.deleteVariantDetails(createSalesDto.getEriksha_variant_details_id());
        session.setAttribute("message", new MessageDTO("Sale Done Successfully", "Success"));
        modelAndView.addObject("message", "Sale Done Successfully");
        modelAndView.setViewName("redirect:/api/home/sales");
        return modelAndView;
    }
    @GetMapping("/bill")
    public ModelAndView bill(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("new-bill");
        return modelAndView;
    }

    @GetMapping("/get/on/road/price")
    public ResponseEntity<String> getOnRoadPrice(@RequestParam String variantId){
        EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(variantId);
        return ResponseEntity.ok(eAutoVariantDetails.getOnRoadPrice());
    }
    
    @GetMapping("/get/on/purchase/price")
    public ResponseEntity<String> getOnPurchasePrice(@RequestParam String variantId){
        EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(variantId);
        String num = Integer.toString(eAutoVariantDetails.getEAutoVariants().getPrice());
        System.out.println(num);
        return ResponseEntity.ok(num);
    }
    
    @GetMapping("/delete/{id}")
    public ModelAndView deletePurchase(@PathVariable String id, HttpSession session){
    	
		int flag = purchaseService.deletePurchaseById(id);
        ModelAndView modelAndView = new ModelAndView();
        if(flag==0) {
            session.setAttribute("message", new MessageDTO("Purchase Deleted Successfully", "Success"));
            modelAndView.addObject("message", "Purchase Deleted Successfully");
        }
        else {
            session.setAttribute("message", new MessageDTO("Purchase could not be deleted as the vehicle is already sold", "Success"));
            modelAndView.addObject("message", "Purchase could not be deleted as the vehicle is already sold");
        }
        modelAndView.setViewName("redirect:/api/sales/getExpense");
        return modelAndView;
    }
    
    
    @GetMapping("/edit/{id}")
    public ModelAndView editPurchase(@PathVariable String id, HttpSession session){
    	
		EAutoVariantDetails eAutoVariantDetails = purchaseService.editPurchaseById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("details",eAutoVariantDetails);
        modelAndView.setViewName("edit-purchase");
        return modelAndView;
    }
    
    @GetMapping("/sale/edit/{id}")
    public ModelAndView editSale(@PathVariable String id, HttpSession session){
    	
		SalesDto eAutoVariantDetails = salesService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("details",eAutoVariantDetails);
        modelAndView.setViewName("edit-sale-payment");
        return modelAndView;
    }
       
    @GetMapping("/sale/delete/{id}")
    public ModelAndView deleteSale(@PathVariable String id, HttpSession session){

		salesService.deleteById(id);
        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("message", new MessageDTO("Sale Deleted Successfully", "Success"));
        modelAndView.addObject("message", "Sale Deleted Successfully");
        modelAndView.setViewName("redirect:/api/sales/getIncome");
        return modelAndView;
    }

    @RequestMapping(path = "/generate/pdf/{id}")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {

        /* Do Business Logic*/
        String orderHtml = null;
        PurchaseDto purchase = purchaseService.getById(id);

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("purchase", purchase);
        if(purchase.getPurchaseType().equals(TypeList.Vehicle.name())) {
            orderHtml = templateEngine.process("new-bill", context);
        }
        else{
            orderHtml = templateEngine.process("new-spare-part-purchase-bill", context);
        }

        /* Setup Source and target I/O streams */

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

        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

    @RequestMapping(path = "/generate/pdf/sales/{id}")
    public ResponseEntity<?> getPDFSales(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {

        /* Do Business Logic*/
        String orderHtml = null;
        SalesDto sales = salesService.getById(id);

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("purchase", sales);
        if(sales.getPurchaseType().equals(TypeList.Vehicle.name())) {
            orderHtml = templateEngine.process("new-sales-bill", context);
        }
        else{
            orderHtml = templateEngine.process("new-spare-part-sale-bill", context);
        }

        /* Setup Source and target I/O streams */

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
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }
   

    @GetMapping
    public ResponseEntity<List<SalesDto>> getAllSales(){
        List<SalesDto> all = salesService.getAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
    @GetMapping("/getAllSales")
    public ModelAndView getAllSalesEntity(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
        List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
        List<MiscellaneousDto> allmisc = miscellaneousService.getAllMiscSales(domainUser);
        modelAndView.addObject("allSales",allSales);
        modelAndView.addObject("allPurchase",allPurchase);
        modelAndView.addObject("allMainLedger",allMainLedger);
        
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.setViewName("accounts");
        return modelAndView;
    }
    
    @GetMapping("/getExpense")
    public ModelAndView getAllPurchase(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
        List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
        List<MiscellaneousDto> allmisc = miscellaneousService.getAllMiscDebit(domainUser);
        modelAndView.addObject("allSales",allSales);
        modelAndView.addObject("allPurchase",allPurchase);
        modelAndView.addObject("allMainLedger",allMainLedger);
        modelAndView.addObject("allmisc",allmisc);
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.setViewName("expense-ledger");
        return modelAndView;
    }
    
    @GetMapping("/getIncome")
    public ModelAndView getAllSaleLedger(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
        List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
        List<MiscellaneousDto> allmisc = miscellaneousService.getAllMiscCredit(domainUser);
        modelAndView.addObject("allSales",allSales);
        modelAndView.addObject("allPurchase",allPurchase);
        modelAndView.addObject("allMainLedger",allMainLedger);
        modelAndView.addObject("allmisc",allmisc);
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.setViewName("income-ledger");
        return modelAndView;
    }
    
    @GetMapping("/partPayment/getIncome")
    public ModelAndView getAllAdvanceSaleLedger(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        List<SalesDto> allSales = salesService.getAllFullPaymentSales(domainUser);
        List<PurchaseDto> allPurchase = purchaseService.getAllFullPurchaseDetails(domainUser);
        List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
        List<MiscellaneousDto> allmisc = miscellaneousService.getAllMiscCredit(domainUser);
        modelAndView.addObject("allSales",allSales);
        modelAndView.addObject("allPurchase",allPurchase);
        modelAndView.addObject("allMainLedger",allMainLedger);
        modelAndView.addObject("allmisc",allmisc);
        modelAndView.addObject("purchase",new PurchaseDto());
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.setViewName("sales-non-paid");
        return modelAndView;
    }
    @GetMapping("/partPayment/updatePage/{id}")
    public ModelAndView updatePaymentPage(@AuthenticationPrincipal DomainUser domainUser, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        SalesDto salesDto=salesService.getById(id);
        CreatePartPaymentDto createPartPaymentDto=new CreatePartPaymentDto();
        createPartPaymentDto.setSales_id(salesDto.getId());
        createPartPaymentDto.setSales_customer_name(salesDto.getCustomer_name());
        createPartPaymentDto.setPurchaseType(salesDto.getPurchaseType());
        createPartPaymentDto.setVariantName(salesDto.getVariantName());
        createPartPaymentDto.setSparePartsName(salesDto.getSparePartsName());
        createPartPaymentDto.setSales_totalPrice(salesDto.getTotalPrice());
        createPartPaymentDto.setCashAmount(salesDto.getCashAmount());
        createPartPaymentDto.setCheckNumber(salesDto.getCheckNumber());
        createPartPaymentDto.setCheckAmount(salesDto.getCheckAmount());
        createPartPaymentDto.setOnlineAmount(salesDto.getOnlineAmount());
        createPartPaymentDto.setOnline_tran_type(salesDto.getOnline_tran_type());
        createPartPaymentDto.setOnline_tran(salesDto.getOnline_tran());
        createPartPaymentDto.setGuarantorAmount(salesDto.getGuarantorAmount());
        modelAndView.addObject("sales",createPartPaymentDto);
        String reg;
        reg=domainUser.getRegion_name();
        modelAndView.addObject("reg", reg);
        //modelAndView.addObject("CreateSalesDto",new CreateSalesDto());
        modelAndView.setViewName("update-payment");
        return modelAndView;
    }
    @PostMapping("/partPayment/update/{id}")
    public ModelAndView updatePartPayment(@AuthenticationPrincipal DomainUser domainUser,@ModelAttribute("sales") CreatePartPaymentDto createPartPaymentDto, @PathVariable String id)
    {
        salesService.updatePart(createPartPaymentDto,id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/api/sales/partPayment/getIncome");
        return modelAndView;
    }
    @GetMapping("/partPayment/DetailsPage/{id}")
    public ModelAndView partPaymentDetailsPage(@AuthenticationPrincipal DomainUser domainUser, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        List<PartPaymentDetailsDTO> list=salesService.getPartPaymentDetails(domainUser,id);
        modelAndView.addObject("PartPaymentDetailsList",list);
        String reg;
        reg=domainUser.getRegion_name();
        modelAndView.addObject("reg", reg);
        modelAndView.setViewName("PartPaymentDetailsPage");
        return modelAndView;
    }
    @RequestMapping(path = "/getIncome/generate/pdf")
	public ResponseEntity<?> getincomePDF(@AuthenticationPrincipal DomainUser domainUser,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String orderHtml = null;
		List<SalesDto> allSales = salesService.getAllSales(domainUser);
		WebContext context = new WebContext(request, response, servletContext);
		
		context.setVariable("allSales", allSales);
		
		orderHtml = templateEngine.process("income-table", context);
		
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=income_table.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
		
	}
    
    @RequestMapping(path = "/getExpense/generate/pdf")
	public ResponseEntity<?> getexpensePDF(@AuthenticationPrincipal DomainUser domainUser,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String orderHtml = null;
		List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
		WebContext context = new WebContext(request, response, servletContext);
		
		context.setVariable("allPurchase", allPurchase);
		
		orderHtml = templateEngine.process("expense-table", context);
		
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expense_table.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
		
	}
    
 
    
    @GetMapping("/bulk/")
    public ResponseEntity<ByteArrayResource> downloadPurchaseData(@AuthenticationPrincipal DomainUser domainUser) {
        String reg;
        reg=domainUser.getRegion_name();
        byte[] data = purchaseService.downloadPurchaseData(reg);
        ByteArrayResource resource = new ByteArrayResource(data);
        String dateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-disposition", "attachment; filename=\"Expense_Ledger_"+dateTimeFormat+"\".xlsx")
                .body(resource);
    }
    
    @GetMapping("/bulk/sales")
    public ResponseEntity<ByteArrayResource> downloadSalesData(@AuthenticationPrincipal DomainUser domainUser) {
        String reg;
        reg=domainUser.getRegion_name();
        byte[] data = salesService.downloadSalesData(reg);
        ByteArrayResource resource = new ByteArrayResource(data);
        String dateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-disposition", "attachment; filename=\"Income_Ledger_"+dateTimeFormat+"\".xlsx")
                .body(resource);
    }


    
    @GetMapping("/getMain")
    public ModelAndView getMainLedger(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
        List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
        List<MiscellaneousDto> allmisc = miscellaneousService.getAllMiscSales(domainUser);
        modelAndView.addObject("allSales",allSales);
        modelAndView.addObject("allPurchase",allPurchase);
        modelAndView.addObject("allMainLedger",allMainLedger);
        
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.setViewName("main-ledger");
        return modelAndView;
    }
    @GetMapping("/dash/mainLedger")
    public ModelAndView getAllSalesDashBoard(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        List<Purchase> allPurchase = purchaseService.getAllPurchase(domainUser);
        List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
        
        modelAndView.addObject("allSales",allSales);
        modelAndView.addObject("allPurchase",allPurchase);
        modelAndView.addObject("allMainLedger",allMainLedger);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("get/vehicle/variants")
    public ResponseEntity<List<EAutoVariantDetailsDto>> getAllVehicleVariants(@AuthenticationPrincipal DomainUser domainUser){
        List<EAutoVariantDetailsDto> eAutoVariantsDtoList = eAutoVariantDetailsService.getFilterVariants(domainUser);
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        List<EAutoVariantDetailsDto> filteredVehicleDto =new ArrayList<>();
        
        
//        int flag=0;
//        for(EAutoVariantDetailsDto e : eAutoVariantsDtoList)
//        {
//        	for(SalesDto sale : allSales)
//        	{
//        		if(e.getChassis_number()==sale.getChassis_number())
//        		{
//        			flag=1;
//        		}
//        	}
//        	if(flag==0)
//        	{
//        		filteredList.add(e);
//        	}
//        	
//        	flag=0;
//        }
        	
        return ResponseEntity.ok(eAutoVariantsDtoList);
    }
    @GetMapping("get/spare/parts/variants")
    public ResponseEntity<List<SparePartsVariantDetailsDTO>> getAllSparePartVariants(@AuthenticationPrincipal DomainUser domainUser){
        List<SparePartsVariantDetailsDTO> sparePartsDTOS = sparePartsVariantDetailsService.getAllSpareVariantDetails();

        return ResponseEntity.ok(sparePartsDTOS);
    }
    @GetMapping("/{sales_id}")
    public ModelAndView getSalesById(@PathVariable String sales_id){
    	ModelAndView modelAndView = new ModelAndView();
        SalesDto byId = salesService.getById(sales_id);
        return modelAndView;
    }
    @DeleteMapping("/{sales_id}")
    public ModelAndView deleteById(@PathVariable String sales_id){
    	ModelAndView modelAndView = new ModelAndView();
        salesService.deleteById(sales_id);
        return modelAndView;
    }
    @PutMapping("/{sales_id}")
    public ModelAndView update(@PathVariable String sales_id,@RequestBody CreateSalesDto createSalesDto,@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
        SalesDto salesDto = salesService.UpdateSales(sales_id, createSalesDto,domainUser);
        return modelAndView;
    }
}
