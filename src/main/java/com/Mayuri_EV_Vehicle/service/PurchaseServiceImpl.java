package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.*;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.*;
import com.Mayuri_EV_Vehicle.util.PurchaseDataReadExcel;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private PurchaseRepository purchaseRepository;
    private SalesRepository salesRepository;
    private VehicleService vehicleService;
    private final VehicleRepository vehicleRepository;
    private final SparePartsRepository sparePartsRepository;
    private final EAutoVariantsService eAutoVariantsService;
    private final EAutoVariantDetailsService eAutoVariantDetailsService;
    private final SparePartsVariantDetailsService sparePartsVariantDetailsService;
    private final SparePartsService sparePartsService;
    private SupplierServcie supplierServcie;
    private PurchasePayementService purchasePayementService;
    private PurchaseDataReadExcel purchaseDataReadExcel;
    private PurchasePartPaymentRepository purchasePartPaymentRepository;
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,SalesRepository salesRepository, VehicleService vehicleService,
                               VehicleRepository vehicleRepository, SparePartsRepository sparePartsRepository, EAutoVariantsService eAutoVariantsService,
                               EAutoVariantDetailsService eAutoVariantDetailsService, SparePartsVariantDetailsService sparePartsVariantDetailsService, SparePartsService sparePartsService, SupplierServcie supplierServcie, PurchasePayementService purchasePayementService,PurchaseDataReadExcel purchaseDataReadExcel,PurchasePartPaymentRepository purchasePartPaymentRepository) {
        this.purchaseRepository = purchaseRepository;
        this.salesRepository= salesRepository;
        this.vehicleService=vehicleService;
        this.vehicleRepository = vehicleRepository;
        this.sparePartsRepository = sparePartsRepository;
        this.eAutoVariantsService = eAutoVariantsService;
        this.eAutoVariantDetailsService = eAutoVariantDetailsService;
        this.sparePartsVariantDetailsService = sparePartsVariantDetailsService;
        this.sparePartsService = sparePartsService;
        this.supplierServcie=supplierServcie;
        this.purchasePayementService=purchasePayementService;
        this.purchaseDataReadExcel = purchaseDataReadExcel;
        this.purchasePartPaymentRepository=purchasePartPaymentRepository;
    }

    @Override
    public PurchaseDto saveOnePurchase(CreatePurchaseDto createPurchaseDto,DomainUser domainUser) {
        Purchase purchase = null;
        String alpha = "ODR"+ RandomStringUtils.randomAlphanumeric(9);
        String invoiceNumber = "GST/"+ RandomStringUtils.randomNumeric(4)+"/"+RandomStringUtils.randomNumeric(4);
        Double gst_price=(createPurchaseDto.getTotalPrice()*createPurchaseDto.getGst())/100;
        double totalPrice=createPurchaseDto.getTotalPrice()+gst_price;
        String output_startDate,output_endDate,output_purchase_date;
        boolean full_payment;
        if(totalPrice==createPurchaseDto.getPerQuantityPrice())
        {
            full_payment=true;
        }
        else {
            full_payment=false;
        }
        if(createPurchaseDto.getBattery_warranty_start_date()!="" && createPurchaseDto.getBattery_warranty_end_date()!="")
        {
            String Battery_startDate =createPurchaseDto.getBattery_warranty_start_date();
            String Battery_endDate =createPurchaseDto.getBattery_warranty_end_date();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate stat_date = LocalDate.parse(Battery_startDate, inputFormatter);
            LocalDate end_date = LocalDate.parse(Battery_endDate, inputFormatter);
            output_startDate = stat_date.format(outputFormatter);
            output_endDate = end_date.format(outputFormatter);
        }
       else {
            output_startDate =null;
            output_endDate=null;
        }
       if(createPurchaseDto.getPurchase_tranaction_date()!="")
       {
           String inputDateString =createPurchaseDto.getPurchase_tranaction_date();
           DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
           LocalDate date = LocalDate.parse(inputDateString, inputFormatter);
           output_purchase_date = date.format(outputFormatter);
       }
       else {
           output_purchase_date=null;
       }
//        if(createPurchaseDto.getPerQuantityPrice()==Double.parseDouble(createSalesDto.getOnlineAmount()+createSalesDto.getCashAmount()+createSalesDto.getGuarantorAmount()+createSalesDto.getCheckAmount()))
//		{
//			full_payment=true;
//		}
//		else
//			full_payment=false;
        PurchasePartPayment purchasePartPayment= null;
        if(createPurchaseDto.getType().equals(TypeList.Vehicle.name())) {
            CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto = new CreateEAutoVariantDetailsDto(createPurchaseDto.getEriksha_variant_details_id(),
                    createPurchaseDto.getChassis_number(), createPurchaseDto.getModel_number(), createPurchaseDto.getEngine_number(), createPurchaseDto.getBattery_number(), createPurchaseDto.getBattery_maker_name(),
                    output_startDate, output_endDate, createPurchaseDto.getMoter_number(), createPurchaseDto.getController_name(), createPurchaseDto.getOnRoadPrice());

            EAutoVariantDetailsDto eAutoVariantDetailsDto = eAutoVariantDetailsService.createVariantDetail(createEAutoVariantDetailsDto, domainUser);

            EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(eAutoVariantDetailsDto.getId());
            purchase = new Purchase(alpha, invoiceNumber, TypeList.valueOf(createPurchaseDto.getType()), eAutoVariantDetails.getId(), null,createPurchaseDto.getManufacturerNumber(), createPurchaseDto.getManufacturerName(),
                    createPurchaseDto.getManufacturerAddress(), createPurchaseDto.getSupplierName(), createPurchaseDto.getSupplierNumber(),
                    createPurchaseDto.getAdharCardNo(), createPurchaseDto.getPanCardNo(), createPurchaseDto.getGstNo(),
                    createPurchaseDto.getSupplierAddress(), createPurchaseDto.getPerQuantityPrice(), totalPrice,
                    Region.valueOf(domainUser.getRegion()), domainUser.getFirstName() + domainUser.getLastName(),domainUser.getRegion_name(),gst_price,full_payment,output_purchase_date);

            Vehicle vehicle = vehicleService.getvehicleById(eAutoVariantDetails.getEAutoVariants().getEAuto().getId());
            vehicle.setQuantity(vehicle.getQuantity()+1);
            vehicleRepository.save(vehicle);
        }
        else{
            CreateSparePartDetailsDTO createSparePartDetailsDTO = new CreateSparePartDetailsDTO(createPurchaseDto.getEriksha_variant_details_id(), createPurchaseDto.getChassis_number(), createPurchaseDto.getModel_number());
            SparePartsVariantDetailsDTO sparePartsVariantDetailsDTO = sparePartsVariantDetailsService.createSparePartVariant(createSparePartDetailsDTO, domainUser);
            SparePartsVariantDetails sparePartsVariantDetails = sparePartsVariantDetailsService.getSpareVariant(sparePartsVariantDetailsDTO.getId());

            purchase = new Purchase(alpha,invoiceNumber,TypeList.valueOf(createPurchaseDto.getType()), null, sparePartsVariantDetails.getId(),createPurchaseDto.getManufacturerNumber(), createPurchaseDto.getManufacturerName(),
                    createPurchaseDto.getManufacturerAddress(), createPurchaseDto.getSupplierName(), createPurchaseDto.getSupplierNumber(),
                    createPurchaseDto.getAdharCardNo(), createPurchaseDto.getPanCardNo(), createPurchaseDto.getGstNo(),
                    createPurchaseDto.getSupplierAddress(), createPurchaseDto.getPerQuantityPrice(), totalPrice,
                    Region.valueOf(domainUser.getRegion()), domainUser.getFirstName() + domainUser.getLastName(),domainUser.getRegion_name(),gst_price,full_payment,output_purchase_date);

            SpareParts spareParts = sparePartsService.getSparePartsById(sparePartsVariantDetails.getSpareParts().getId());

            spareParts.setQuantity(spareParts.getQuantity()+1);
            sparePartsRepository.save(spareParts);

        }

        Purchase save = purchaseRepository.save(purchase);
        purchasePartPayment=new PurchasePartPayment(purchase.getPerQuantityPrice(),alpha,purchase);
        purchasePartPaymentRepository.save(purchasePartPayment);

//        SupplierDto save1 = supplierServcie.save(createPurchaseDto);
//        PurchasePaymentDto save2 = purchasePayementService.save(createPurchaseDto);
        return convertToDto(save);
    }

    @Override
    public List<PurchaseDto> getAll() {
        List<Purchase> all = purchaseRepository.findAll();
        List<PurchaseDto> allPurchaseDto=new ArrayList<>();
        for (Purchase purchase : all) {
            PurchaseDto purchaseDto = convertToDto(purchase);
            allPurchaseDto.add(purchaseDto);
        }
        return allPurchaseDto;
    }

    @Override
    public PurchaseDto getById(String purchaseId) {
        Optional<Purchase> byId = purchaseRepository.findById(purchaseId);
        Purchase purchase = byId.get();
        return convertToDto(purchase);
    }

    @Override
    public void deleteById(String purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }
    
    @Override
    public int deletePurchaseById(String id)
    {
    	Optional<Purchase> byId = purchaseRepository.findById(id);
        Purchase purchase = byId.get();
    	EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(purchase.getEAutoVariantDetails());
    	
    	int flag=0;
    	List<Sales> all = salesRepository.findAll();
    	for (Sales sales : all) {
            if(sales.getEAutoVariantDetails().equals(eAutoVariantDetails.getId()))
            {
            	flag=1;
            	break;
            }
        }
    	if(flag==0)
    	{
    	EAutoVariants eAutoVariants = eAutoVariantsService.get_variant_by_id(eAutoVariantDetails.getEAutoVariants().getId());
    	Vehicle vehicle = vehicleService.getvehicleById(eAutoVariants.getEAuto().getId());
        List<PurchasePartPayment> PartPaymentDetailsList=purchasePartPaymentRepository.findAllByPurchase_id(id);
            for(PurchasePartPayment purchasePartPayment:PartPaymentDetailsList)
            {
                purchasePartPaymentRepository.delete(purchasePartPayment);
            }
        purchaseRepository.delete(purchase);
    	eAutoVariantDetailsService.deleteVariantDetails(eAutoVariantDetails.getId());
    	eAutoVariantsService.deleteById(eAutoVariants.getId());
    	vehicleService.deleteVehicle(vehicle.getId());

    	}
    	return flag;
    }
    
    @Override
    public EAutoVariantDetails editPurchaseById(String id)
    {
    	Optional<Purchase> byId = purchaseRepository.findById(id);
        Purchase purchase = byId.get();
    	EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(purchase.getEAutoVariantDetails());
    	
    	
    	return eAutoVariantDetails;
    }
    	
    	
    	
    @Override
    public PurchaseDto update(String purchaseId, CreatePurchaseDto createPurchaseDto,DomainUser domainUser) {
//        Optional<Purchase> byId = purchaseRepository.findById(purchaseId);
//        Purchase purchase = byId.get();
//        Purchase purchase1=new Purchase(purchase.getId(),createPurchaseDto.getCategories(), createPurchaseDto.getSub_categories(),
//                createPurchaseDto.getVariants(),createPurchaseDto.getColor(),createPurchaseDto.getChassis_number(),
//                createPurchaseDto.getModel_number(), createPurchaseDto.getEngine_number(),
//                createPurchaseDto.getManufacturer_name(),createPurchaseDto.getManufacturer_number(),
//                createPurchaseDto.getManufacturer_address(),createPurchaseDto.getName(),createPurchaseDto.getPurcharseDate(),
//                createPurchaseDto.getSupplier_name(),createPurchaseDto.getTotalPrice(),Region.valueOf(domainUser.getRegion()));
//        Purchase save = purchaseRepository.save(purchase1);
        return null;
    }

    public PurchaseDto convertToDto(Purchase purchase){

        if(purchase == null){
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

        if(purchase.getType().equals(TypeList.Vehicle)){
            eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(purchase.getEAutoVariantDetails());
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
            sparePartsVariantDetails = sparePartsVariantDetailsService.getSpareVariant(purchase.getSparePartVariantDetailsId());
            variantId = sparePartsVariantDetails.getId();
            modelNumber = sparePartsVariantDetails.getModel_number();
            chassisNumber = sparePartsVariantDetails.getChassis_number();
            purchaseType = TypeList.Spare_Parts.name();
            sparePartsName = sparePartsVariantDetails.getSpareParts().getName();
            companyName = sparePartsVariantDetails.getSpareParts().getCompanyName();
        }

        PurchaseDto purchaseDto=new PurchaseDto(purchase.getId(), purchase.getOrderId(), purchase.getInvoiceNumber(),purchaseType,variantId,variantName, variantColour, sparePartsName,
                companyName, vehicleType, chassisNumber, modelNumber, engineNumber,motorNumber,
                purchase.getManufacturerNumber(), purchase.getManufacturerName(), purchase.getManufacturerAddress(),
                purchase.getSupplierName(), purchase.getSupplierNumber(), purchase.getAdharCardNo(), purchase.getPanCardNo(), purchase.getGstNo(),
                purchase.getSupplierAddress(), purchase.getPerQuantityPrice(), purchase.getTotalPrice(), onRoadPrice,purchase.getRegion().name(), purchase.getPurchaseDate().toString(),
                purchase.getCreatedBy(),purchase.getGst(),purchase.getPurchase_tranaction_date());
        return purchaseDto;
    }

	@Override
	public List<Purchase> getAllPurchase(DomainUser domainUser) {
		 List<Purchase> all = purchaseRepository.findAll();
        List<Purchase> filteredPurchases = new ArrayList<>();
         if(all.size() != 0) {
             if (domainUser.getRegion_name().equals("ALL")) {
                 return all;
             }

             for (Purchase purchase : all) {
                 if (purchase.getRegion_name().equals(domainUser.getRegion_name())) {
                     filteredPurchases.add(purchase);
                 }
             }
         }
		    return filteredPurchases;	
	}

    @Override
    public List<PurchaseDto> getAllPurchaseDetails(DomainUser domainUser) {
        List<Purchase> all = purchaseRepository.findAll();
        List<Purchase> filteredPurchases = new ArrayList<>();
        if(all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
                return all.stream().map(this::convertToDto).collect(Collectors.toList());
            }

            for (Purchase purchase : all) {
                if (purchase.getRegion_name().equals(domainUser.getRegion_name())) {
                    filteredPurchases.add(purchase);
                }
            }
        }
        return filteredPurchases.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Override
    public List<PurchaseDto> getAllFullPurchaseDetails(DomainUser domainUser) {
        if (domainUser.getRegion() == null) {
            domainUser.setRegion("ALL");
        }
        List<Purchase> all = purchaseRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseDate"));
        List<Purchase> filteredPurchases = new ArrayList<>();
        if(all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
                for (Purchase purchase : all) {
                    if (!purchase.isFull_payment()) {
                        filteredPurchases.add(purchase);
                    }
                }
                return filteredPurchases.stream().map(this::convertToDto).collect(Collectors.toList());
            }
            for (Purchase purchase : all) {
                if (purchase.getRegion_name().equals(domainUser.getRegion_name()) && (!purchase.isFull_payment())) {
                    filteredPurchases.add(purchase);
                }
            }
        }
        return filteredPurchases.stream().map(this::convertToDto).collect(Collectors.toList());
    }



    @Override
	public List<Purchase> getByRegion(String region,DashBoard dashBoard) {
		List<Purchase> findByRegion = purchaseRepository.findByRegion(region);
		List<Purchase> filteredPurchase=new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for(Purchase purchase : findByRegion) {
			try {
				Date purchaseDate = dateFormat.parse(purchase.getPurchaseDate().toString());
				Date fromDate = dateFormat.parse(dashBoard.getLedgerFromDate());
			    Date toDate = dateFormat.parse(dashBoard.getLedgerToDate());
			    if (purchaseDate.compareTo(fromDate) >= 0 && purchaseDate.compareTo(toDate) <= 0) {
			    	filteredPurchase.add(purchase);
			    }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    
		}
		return filteredPurchase;
	}

	@Override
	public byte[] downloadPurchaseData(String reg) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            purchaseDataReadExcel.writePurchaseData(workbook,reg);
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to download".getBytes(StandardCharsets.UTF_8);
        }
	}

    @Override
    public void updatePart(PurchaseDto purchaseDto, String id) {
        Purchase purchase=purchaseRepository.findById(id).get();
        purchase.setPerQuantityPrice(purchaseDto.getPerQuantityPrice());
        if(purchase.getTotalPrice().equals(purchase.getPerQuantityPrice()))
        {
            purchaseRepository.updatePaymentFull(purchase.getPerQuantityPrice(),true,id);
        }
        else
        {
            purchaseRepository.updatePayment(purchase.getPerQuantityPrice(),id);
        }
        PurchasePartPayment purchasePartPayment=new PurchasePartPayment(purchase.getPerQuantityPrice(),id,purchase);
        purchasePartPaymentRepository.save(purchasePartPayment);
    }

    @Override
    public List<PurchasePartPaymentDTO> getPartPaymentDetails(DomainUser domainUser, String id) {
        List<PurchasePartPayment> PartPaymentDetailsList=purchasePartPaymentRepository.findAllByPurchase_id(id);
        List<PurchasePartPaymentDTO> ConvertIntoDtoList=new ArrayList<>();
        for(PurchasePartPayment purchasePartPayment:PartPaymentDetailsList)
        {
            PurchasePartPaymentDTO partPaymentDetailsDTO=new PurchasePartPaymentDTO(purchasePartPayment.getId(),purchasePartPayment.getCreatedOn(),purchasePartPayment.getPerQuantityPrice(),purchasePartPayment.getPurchase_id());
            ConvertIntoDtoList.add(partPaymentDetailsDTO);
        }
        return ConvertIntoDtoList;

    }

}
