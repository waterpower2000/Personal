package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.*;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.*;
import com.Mayuri_EV_Vehicle.util.SalesDataReadExcel;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService{
    private SalesRepository salesRepository;
    private VehicleService vehicleService;
	private SparePartsService sparePartsService;
    private EAutoService eAutoService;

	private  SparePartsVariantDetailsService sparePartsVariantDetailsService;
    private final VehicleRepository vehicleRepository;
	private final SparePartsRepository sparePartsRepository;
    private final EAutoVariantDetailsService eAutoVariantDetailsService;
    private ERikshaService eRikshaService;
    private CustomerService customerService;
    private SalesPaymentService salesPaymentService;
	private EAutoVariantDetailsRepository autoVariantDetailsRepository;
	private SalesDataReadExcel salesDataReadExcel;
    private PartPaymentRepositoty partPaymentRepositoty;
    public SalesServiceImpl(SalesRepository salesRepository,
							VehicleService vehicleService,
							SparePartsService sparePartsService, @Lazy EAutoService eAutoService,
							SparePartsVariantDetailsService sparePartsVariantDetailsService, SparePartsRepository sparePartsRepository, @Lazy ERikshaService eRikshaService,
							CustomerService customerService,
							SalesPaymentService salesPaymentService, VehicleRepository vehicleRepository,
							EAutoVariantDetailsService eAutoVariantDetailsService,
							EAutoVariantDetailsRepository autoVariantDetailsRepository,SalesDataReadExcel salesDataReadExcel,PartPaymentRepositoty partPaymentRepositoty) {
        this.salesRepository = salesRepository;
        this.vehicleService = vehicleService;
		this.sparePartsService = sparePartsService;
		this.eAutoService = eAutoService;
		this.sparePartsVariantDetailsService = sparePartsVariantDetailsService;
		this.sparePartsRepository = sparePartsRepository;
		this.vehicleRepository= vehicleRepository;
        this.eAutoVariantDetailsService = eAutoVariantDetailsService;
        this.eRikshaService = eRikshaService;
        this.customerService=customerService;
        this.salesPaymentService=salesPaymentService;
        this.autoVariantDetailsRepository = autoVariantDetailsRepository;
        this.salesDataReadExcel=salesDataReadExcel;
        this.partPaymentRepositoty=partPaymentRepositoty;
    }

	@Override
	public void updatePart(CreatePartPaymentDto createPartPaymentDto, String id) {
		Sales sales=salesRepository.findById(id).get();
		if(createPartPaymentDto.getCashAmount()==""  || createPartPaymentDto.getCashAmount()==null)
		{
			sales.setCashAmount("0");
		}
		else {
			sales.setCashAmount(createPartPaymentDto.getCashAmount());
		}
		if(createPartPaymentDto.getCheckAmount()==""  || createPartPaymentDto.getCheckAmount()==null)
		{
			sales.setCheckAmount("0");
		}
		else {
			sales.setCheckAmount(createPartPaymentDto.getCheckAmount());
		}
		if(createPartPaymentDto.getOnlineAmount()==""  || createPartPaymentDto.getOnlineAmount()==null)
		{
			sales.setOnlineAmount("0");
		}
		else {
			sales.setOnlineAmount(createPartPaymentDto.getOnlineAmount());
		}
		if(createPartPaymentDto.getGuarantorAmount()==""  || createPartPaymentDto.getGuarantorAmount()==null)
		{
			sales.setGuarantorAmount("0");
		}
		else {
			sales.setGuarantorAmount(createPartPaymentDto.getGuarantorAmount());
		}

		sales.setCheckNumber(createPartPaymentDto.getCheckNumber());
		sales.setOnline_tran_type(createPartPaymentDto.getOnline_tran_type());
		sales.setOnline_tran(createPartPaymentDto.getOnline_tran());
		double PerQuantityPrice=sales.getPerQuantityPrice();
		sales.setSales_totalPrice(Double.parseDouble(sales.getCashAmount())+Double.parseDouble(sales.getCheckAmount())+Double.parseDouble(sales.getOnlineAmount())+Double.parseDouble(sales.getGuarantorAmount()));
		if(PerQuantityPrice==sales.getSales_totalPrice())
		{
			salesRepository.updatePaymentFull(sales.getCashAmount(),sales.getCheckAmount(),sales.getOnlineAmount(),sales.getGuarantorAmount(),sales.getSales_totalPrice(),sales.getPerQuantityPrice(),sales.getCheckNumber(),sales.getOnline_tran_type(),sales.getOnline_tran(),true,sales.getId());
		}
		else
		{
			salesRepository.updatePayment(sales.getCashAmount(),sales.getCheckAmount(),sales.getOnlineAmount(),sales.getGuarantorAmount(),sales.getSales_totalPrice(),sales.getPerQuantityPrice(),sales.getCheckNumber(),sales.getOnline_tran_type(),sales.getOnline_tran(),sales.getId());
		}
        PartPayment partPayment=new PartPayment(id,createPartPaymentDto.getSales_customer_name(),sales.getSales_totalPrice(),createPartPaymentDto.getCashAmount(),createPartPaymentDto.getCheckNumber(),createPartPaymentDto.getCheckAmount(),createPartPaymentDto.getOnline_tran_type(),createPartPaymentDto.getOnline_tran(),createPartPaymentDto.getOnlineAmount(),createPartPaymentDto.getGuarantorAmount(),sales,createPartPaymentDto.getTranaction_date());
        partPaymentRepositoty.save(partPayment);
    }

	@Override
    public SalesDto saveOneSales(CreateSalesDto createSalesDto,DomainUser domainUser) {

		Sales sales = null;
		String alpha = "ODR"+ RandomStringUtils.randomAlphanumeric(9);
		String invoiceNumber = "GST/"+ RandomStringUtils.randomNumeric(4)+"/"+RandomStringUtils.randomNumeric(4);
    	Double charges=Double.parseDouble(createSalesDto.getLicenseCharge())+Double.parseDouble(createSalesDto.getHypoCharge())+Double.parseDouble(createSalesDto.getInsuranceCharge())+Double.parseDouble(createSalesDto.getHsrfCharge())+Double.parseDouble(createSalesDto.getRegistrationCharge());
		Double sales_gst=(((100*(createSalesDto.getSalesPrice()-charges))/(createSalesDto.getSales_gst()+100))*createSalesDto.getSales_gst())/100;
		String output_startDate,output_endDate,output_sales_date;
		boolean full_payment;
		if(createSalesDto.getCashAmount()==null || createSalesDto.getCashAmount()=="")
			createSalesDto.setCashAmount("0");
		if(createSalesDto.getCheckAmount()==null || createSalesDto.getCheckAmount()=="")
			createSalesDto.setCheckAmount("0");
		if(createSalesDto.getOnlineAmount()==null || createSalesDto.getOnlineAmount()=="")
			createSalesDto.setOnlineAmount("0");
		if(createSalesDto.getGuarantorAmount()==null || createSalesDto.getGuarantorAmount()=="")
			createSalesDto.setGuarantorAmount("0");
		if(createSalesDto.getBattery_start()!="" && createSalesDto.getBattery_end()!="")
		{
			String Battery_startDate =createSalesDto.getBattery_start();
			String Battery_endDate =createSalesDto.getBattery_end();
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate stat_date = LocalDate.parse(Battery_startDate, inputFormatter);
			LocalDate end_date = LocalDate.parse(Battery_endDate, inputFormatter);
			output_startDate = stat_date.format(outputFormatter);
			output_endDate = end_date.format(outputFormatter);
		}
		else {
			output_startDate =null;
			output_endDate =null;
		}
		if(createSalesDto.getSales_tranaction_date()!="" )
		{
			String inputDateString =createSalesDto.getSales_tranaction_date();
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate date = LocalDate.parse(inputDateString, inputFormatter);
			output_sales_date = date.format(outputFormatter);
		}
		else
		{
			output_sales_date=null;
		}
		if(createSalesDto.getSalesPrice()==Double.parseDouble(createSalesDto.getOnlineAmount()+createSalesDto.getCashAmount()+createSalesDto.getGuarantorAmount()+createSalesDto.getCheckAmount()))
		{
			full_payment=true;
		}
		else
			full_payment=false;

    	if(createSalesDto.getType().equals(TypeList.Vehicle.name())) {
			EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(createSalesDto.getEriksha_variant_details_id());
			 sales =new Sales(alpha, invoiceNumber, TypeList.valueOf(createSalesDto.getType()), eAutoVariantDetails.getId(), null,createSalesDto.getSales_customer_name(), createSalesDto.getSales_customer_number(),
					createSalesDto.getSales_customer_AadhaarCard(), createSalesDto.getSales_customer_PanCard(), createSalesDto.getSales_customer_GSTNumber(),
					createSalesDto.getSales_customer_address(),createSalesDto.getBattery_1(),createSalesDto.getBattery_2(),createSalesDto.getBattery_3(),createSalesDto.getBattery_4(),createSalesDto.getBattery_5(),
					 output_startDate,output_endDate, createSalesDto.getGurranterName(), createSalesDto.getGurranterPhoneNumber(), createSalesDto.getGurranterAddress(),
					createSalesDto.getRegistrationCharge(),createSalesDto.getInsuranceCharge(),createSalesDto.getHypoCharge(),createSalesDto.getHsrfCharge(),createSalesDto.getLicenseCharge(),
					createSalesDto.getSalesPrice(),createSalesDto.getDiscount(),sales_gst, Double.parseDouble(createSalesDto.getOnlineAmount())+Double.parseDouble(createSalesDto.getCheckAmount())+Double.parseDouble(createSalesDto.getCashAmount())+Double.parseDouble(createSalesDto.getGuarantorAmount()),
					createSalesDto.getPaymentMode(),createSalesDto.getCashAmount(), createSalesDto.getCheckNumber(), createSalesDto.getBankName(), createSalesDto.getCheckAmount(),
					createSalesDto.getOnline_tran_type(),createSalesDto.getOnline_tran(),createSalesDto.getOnlineAmount(),createSalesDto.getGuarantorAmount(), 
					Region.valueOf(domainUser.getRegion()), domainUser.getFirstName()+domainUser.getLastName() ,domainUser.getRegion_name(),full_payment,output_sales_date);


			
			Vehicle vehicle = vehicleService.getvehicleById(eAutoVariantDetails.getEAutoVariants().getEAuto().getId());
			vehicle.setQuantity(vehicle.getQuantity()-1);
			
			vehicleRepository.save(vehicle);

		}
		else{

			SparePartsVariantDetails sparePartsVariantDetails = sparePartsVariantDetailsService.getSpareVariant(createSalesDto.getEriksha_variant_details_id());
			sales =new Sales(alpha, invoiceNumber, TypeList.valueOf(createSalesDto.getType()), null, sparePartsVariantDetails.getId(),createSalesDto.getSales_customer_name(), createSalesDto.getSales_customer_number(),
					createSalesDto.getSales_customer_AadhaarCard(), createSalesDto.getSales_customer_PanCard(), createSalesDto.getSales_customer_GSTNumber(),
					createSalesDto.getSales_customer_address(),createSalesDto.getBattery_1(),createSalesDto.getBattery_2(),createSalesDto.getBattery_3(),createSalesDto.getBattery_4(),createSalesDto.getBattery_5(),
					output_startDate,output_endDate,
					createSalesDto.getGurranterName(), createSalesDto.getGurranterPhoneNumber(), createSalesDto.getGurranterAddress(),
					createSalesDto.getRegistrationCharge(),createSalesDto.getInsuranceCharge(),createSalesDto.getHypoCharge(),createSalesDto.getHsrfCharge(),createSalesDto.getLicenseCharge(),
					createSalesDto.getSalesPrice(),createSalesDto.getDiscount(),sales_gst, (createSalesDto.getSalesPrice()),
					createSalesDto.getPaymentMode(),createSalesDto.getCashAmount(), createSalesDto.getCheckNumber(), createSalesDto.getBankName(), createSalesDto.getCheckAmount(),createSalesDto.getOnline_tran_type(),createSalesDto.getOnline_tran(),createSalesDto.getOnlineAmount(),createSalesDto.getGuarantorAmount() ,Region.valueOf(domainUser.getRegion()), domainUser.getFirstName()+domainUser.getLastName()
					,domainUser.getRegion_name(),full_payment,output_sales_date);



			SpareParts spareParts = sparePartsService.getSparePartsById(sparePartsVariantDetails.getSpareParts().getId());
			spareParts.setQuantity(spareParts.getQuantity()-1);
			sparePartsRepository.save(spareParts);

		}

		Sales save = salesRepository.save(sales);
		PartPayment partPayment=new PartPayment(alpha,createSalesDto.getSales_customer_name(),sales.getSales_totalPrice(),createSalesDto.getCashAmount(),createSalesDto.getCheckNumber(),createSalesDto.getCheckAmount(),createSalesDto.getOnline_tran_type(),createSalesDto.getOnline_tran(),createSalesDto.getOnlineAmount(),createSalesDto.getGuarantorAmount(),sales,createSalesDto.getSales_tranaction_date());
		partPaymentRepositoty.save(partPayment);
        return convertToDto(save);
    }

    @Override
    public List<SalesDto> getAll() {
        List<Sales> all = salesRepository.findAll();
        List<SalesDto> allSalesDto=new ArrayList<>();
        for (Sales sales : all) {
            SalesDto salesDto = convertToDto(sales);
            allSalesDto.add(salesDto);
        }
        return allSalesDto;
    }
    

    
    @Override
    public SalesDto getById(String salesId) {
        Optional<Sales> byId = salesRepository.findById(salesId);
        return convertToDto(byId.get());
    }
    
    @Override
	public SalesDto editById(String salesId) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void deleteById(String salesId) {
		List<PartPayment> PartPaymentDetailsList=partPaymentRepositoty.findAllBySales_id(salesId);
		for(PartPayment partPayment:PartPaymentDetailsList)
		{
			partPaymentRepositoty.delete(partPayment);
		}
        salesRepository.deleteById(salesId);
    }

    @Override
    public SalesDto UpdateSales(String salesId,CreateSalesDto createSalesDto,DomainUser domainUser) {
//        Optional<Sales> byId = salesRepository.findById(salesId);
//        Sales sales = byId.get();
//        Sales sales1 =new Sales(sales.getId(),createSalesDto.getSales_categories(),createSalesDto.getSales_sub_categories(),
//        		createSalesDto.getSales_variants(),createSalesDto.getSales_color(),createSalesDto.getSales_name(),createSalesDto.getSales_purcharseDate(),
//        		createSalesDto.getSales_chassis_number(),createSalesDto.getSales_model_number(),createSalesDto.getSales_engine_number(),
//        		createSalesDto.getSales_manufacturer_name(),createSalesDto.getSales_manufacturer_number(),
//        		createSalesDto.getSales_manufacturer_address(),createSalesDto.getSales_customer_name(),createSalesDto.getSales_totalPrice(),
//        		sales.getRegion());
//        Sales save = salesRepository.save(sales1);
        return null;
    }

    public SalesDto convertToDto(Sales sales){
		if(sales == null){
			return null;
		}

		EAutoVariantDetails eAutoVariantDetails = null;
		SparePartsVariantDetails sparePartsVariantDetails = null;
		String variantId = null;
		String modelNumber = null;
		String chassisNumber = null;
		String engineNumber = null;
		String motorNumber = null;
		String purchaseType = null;
		String vehicleType = null;
		String variantName = null;
		String variantColour = null;
		String sparePartsName = null;
		String companyName = null;
		String onRoadPrice = null;
		String gurranterName = sales.getGurranterName() != null ? sales.getGurranterName() : null;
		String gurranterPhNo = sales.getGurranterPhoneNumber() != null ? sales.getGurranterPhoneNumber() : null;
		String gurranterAddress = sales.getGurranterAddress() != null ? sales.getGurranterAddress() : null;
		String regCharge = sales.getRegCharge() != null ? sales.getRegCharge() : "0";
		String insCharge = sales.getInsCharge() != null ? sales.getInsCharge() : "0";
		String hypoCharge =sales.getHypoCharge() != null ? sales.getHypoCharge() : "0";
		String hsrfCharge =sales.getHsrfCharge() != null ? sales.getHsrfCharge() : "0";
		String licenseCharge = sales.getLicenseCharge() != null ? sales.getLicenseCharge() : "0";
		String cashAmount=sales.getCashAmount() !=null? sales.getCashAmount():"0";
		String checkNo = sales.getCheckNumber() != null ? sales.getCheckNumber() : null;
		String bankName = sales.getBankName() != null ? sales.getBankName() : null;
		String amount = sales.getCheckAmount() != null ? sales.getCheckAmount() : null;
		String discount = sales.getDiscount()!= null ? sales.getDiscount() : "0";
		String online_tran_type = sales.getOnline_tran_type() != null ? sales.getOnline_tran_type():null;
		String online_tran = sales.getOnline_tran() != null ? sales.getOnline_tran():"0";
		String onlineAmount = sales.getOnlineAmount() != null? sales.getOnlineAmount():"0";
		String guarantorAmount = sales.getGuarantorAmount()!= null? sales.getGuarantorAmount():"0";

		if(sales.getType().equals(TypeList.Vehicle)){
			eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(sales.getEAutoVariantDetails());
			variantId = eAutoVariantDetails.getId();
			variantName = eAutoVariantDetails.getEAutoVariants().getVariantName();
			modelNumber = eAutoVariantDetails.getModel_number();
			chassisNumber = eAutoVariantDetails.getChassis_number();
			engineNumber = eAutoVariantDetails.getEngine_number();
			motorNumber = eAutoVariantDetails.getMoter_number();
			purchaseType = TypeList.Vehicle.name();
			vehicleType = eAutoVariantDetails.getEAutoVariants().getEAuto().getVechileType().name();
			variantColour =  eAutoVariantDetails.getEAutoVariants().getColor();
			companyName =  eAutoVariantDetails.getEAutoVariants().getEAuto().getCompanyName();
			onRoadPrice = eAutoVariantDetails.getOnRoadPrice();
		}
		else{
			sparePartsVariantDetails = sparePartsVariantDetailsService.getSpareVariant(sales.getSparePartVariantDetailsId());
			variantId = sparePartsVariantDetails.getId();
			modelNumber = sparePartsVariantDetails.getModel_number();
			chassisNumber = sparePartsVariantDetails.getChassis_number();
			purchaseType = TypeList.Spare_Parts.name();
			sparePartsName = sparePartsVariantDetails.getSpareParts().getName();
			companyName = sparePartsVariantDetails.getSpareParts().getCompanyName();
		}
        SalesDto salesDto=new SalesDto(sales.getId(),sales.getOrderId(), sales.getInvoiceNumber(), purchaseType,variantId,variantName, variantColour, sparePartsName,
				companyName, vehicleType, chassisNumber, modelNumber, engineNumber, motorNumber,
        		sales.getCustomerName(), sales.getCustomerNumber(), sales.getAdharCardNo(),
				sales.getPanCardNo(), sales.getGstNo(), sales.getCustomerAddress(), gurranterName,
				gurranterPhNo, gurranterAddress,
				regCharge,insCharge,hypoCharge,hsrfCharge,licenseCharge,
				sales.getPerQuantityPrice(),discount, sales.getSales_totalPrice(),sales.getCashAmount(),
				sales.getPaymentMode(), checkNo, bankName, amount,online_tran_type,online_tran,onlineAmount,guarantorAmount,sales.getRegion().name(),
				sales.getSaleDate().toString(),
        		sales.getCreatedBy(),sales.getGst_sales(),sales.getSales_tranaction_date());
        return salesDto;
    }

	@Override
	public List<SalesDto> getAllSales(DomainUser domainUser) {
		if (domainUser.getRegion() == null) {
	        domainUser.setRegion("ALL");
	    }
		List<Sales> all = salesRepository.findAll(Sort.by(Sort.Direction.DESC, "saleDate"));
		
		 if(domainUser.getRegion_name().equals("ALL")) {
			 return all.stream().map(this::convertToDto).collect(Collectors.toList());
		 }
		 List<Sales> filteredSales = new ArrayList<>();
		    for (Sales sales : all) {
		        if (sales.getRegion_name().equals(domainUser.getRegion_name())) {
		        	filteredSales.add(sales);
		        }
		    }
		    return filteredSales.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	@Override
	public List<SalesDto> getAllFullPaymentSales(DomainUser domainUser) {
		if (domainUser.getRegion() == null) {
	        domainUser.setRegion("ALL");
	    }
		List<Sales> filteredSales = new ArrayList<>();
		List<Sales> all = salesRepository.findAll(Sort.by(Sort.Direction.DESC, "saleDate"));
		
		 if(domainUser.getRegion_name().equals("ALL")) {
			 for (Sales sales : all) {
			        if (!sales.isFull_payment()) {
			        	filteredSales.add(sales);
			        }
			    }
			 return filteredSales.stream().map(this::convertToDto).collect(Collectors.toList());
		 }
		 
		    for (Sales sales : all) {
		        if (sales.getRegion_name().equals(domainUser.getRegion_name()) && (!sales.isFull_payment())) {
		        	filteredSales.add(sales);
		        }
		    }
		    return filteredSales.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<Sales> getByRegion(String region,DashBoard dashBoard) {
		List<Sales> findByRegion = salesRepository.findByRegion(region);
		List<Sales> filteredSale=new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for(Sales sale : findByRegion) {
			try {
				Date saleDate = dateFormat.parse(sale.getSaleDate().toString());
				Date fromDate = dateFormat.parse(dashBoard.getLedgerFromDate());
			    Date toDate = dateFormat.parse(dashBoard.getLedgerToDate());
			    if (saleDate.compareTo(fromDate) >= 0 && saleDate.compareTo(toDate) <= 0) {
			    	filteredSale.add(sale);
			    }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    
		}
		return filteredSale;
	}

	@Override
	public byte[] downloadSalesData(String reg) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            salesDataReadExcel.writePurchaseData(workbook,reg);
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to download".getBytes(StandardCharsets.UTF_8);
        }
	}

	@Override
	public List<PartPaymentDetailsDTO> getPartPaymentDetails(DomainUser domainUser, String id) {
		List<PartPayment> PartPaymentDetailsList=partPaymentRepositoty.findAllBySales_id(id);
		Comparator<PartPayment> dateComparator = Comparator.comparing(PartPayment::getTranaction_date);
		Collections.sort(PartPaymentDetailsList, dateComparator);
		List<PartPaymentDetailsDTO> ConvertIntoDtoList=new ArrayList<>();
		for(PartPayment partPayment:PartPaymentDetailsList)
		{
			String str=partPayment.getTranaction_date();
			String date=null;
			if(str!=null || str!="")
			{
				date=str.substring(8,10)+"-"+str.substring(5,7)+"-"+str.substring(0,4);
			}
			PartPaymentDetailsDTO partPaymentDetailsDTO=new PartPaymentDetailsDTO(partPayment.getId(),partPayment.getSales_id(),partPayment.getSales_customer_name(),partPayment.getSales_totalPrice(),partPayment.getCashAmount(),partPayment.getCheckNumber(),partPayment.getCheckAmount(),partPayment.getOnline_tran_type(),partPayment.getOnline_tran(),partPayment.getOnlineAmount(),partPayment.getGuarantorAmount(),partPayment.getCreatedOn().toString(),date);
			ConvertIntoDtoList.add(partPaymentDetailsDTO);
		}
		return ConvertIntoDtoList;
	}

}
