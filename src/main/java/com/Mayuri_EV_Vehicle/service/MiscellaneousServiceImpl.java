package com.Mayuri_EV_Vehicle.service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.*;
import com.Mayuri_EV_Vehicle.repository.MiscellaneousHeadsRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.model.RegionTable;
import com.Mayuri_EV_Vehicle.repository.MiscellaneousRepository;
import com.Mayuri_EV_Vehicle.util.MiscellaneousDataReadExcel;


@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {
	
	MiscellaneousRepository miscellaneousRepository;
	MiscellaneousDataReadExcel miscellaneousDataReadExcel;
	MiscellaneousHeadsRepository miscellaneousHeadsRepository;
	public MiscellaneousServiceImpl(MiscellaneousRepository miscellaneousRepository,MiscellaneousDataReadExcel miscellaneousDataReadExcel,MiscellaneousHeadsRepository miscellaneousHeadsRepository) {
		
		this.miscellaneousRepository = miscellaneousRepository;
		this.miscellaneousDataReadExcel = miscellaneousDataReadExcel;
		this.miscellaneousHeadsRepository=miscellaneousHeadsRepository;
	}

	@Override
	public MiscellaneousDto saveMiscellaneous(CreateMiscellaneousDto createMiscellaneousDto, DomainUser domainUser) {
		Miscellaneous miscellaneous = null;

		String inputDateString =createMiscellaneousDto.getDate();
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(inputDateString, inputFormatter);
		String outputDateString = date.format(outputFormatter);
		Optional<Miscellanous_heads> miscellanous_heads=miscellaneousHeadsRepository.findById(createMiscellaneousDto.getMiscellanous_heads_id());
		Miscellanous_heads miscellanous_head=miscellanous_heads.get();
			miscellaneous = new Miscellaneous(createMiscellaneousDto.getMoney_type(),outputDateString,
					createMiscellaneousDto.getAmount(),createMiscellaneousDto.getOthers_specification(),domainUser.getRegion_name(),miscellanous_head);
		
		Miscellaneous save = miscellaneousRepository.save(miscellaneous);
		
		return convertToDto(save);
	}
	
	public MiscellaneousDto convertToDto(Miscellaneous miscellaneous)
	{
		MiscellaneousDto miscellaneousDto = new MiscellaneousDto(miscellaneous.getId(),miscellaneous.getMoney_type(),miscellaneous.getDate(),miscellaneous.getMiscellanous_heads().getTransaction_name(),
				miscellaneous.getAmount(),miscellaneous.getSpecifications(),miscellaneous.getRegion(),miscellaneous.getCreatedOn(),miscellaneous.getMiscellanous_heads().getAccount_type());
		return miscellaneousDto;
	}
	
	@Override
	public List<MiscellaneousDto> getAllMiscSales(DomainUser domainUser) {
		if (domainUser.getRegion() == null) {
	        domainUser.setRegion("ALL");
	    }
		List<Miscellaneous> all = miscellaneousRepository.findAll();
		
		 if(domainUser.getRegion_name().equals("ALL")) {
			 return all.stream().map(this::convertToDto).collect(Collectors.toList());
		 }
		 List<Miscellaneous> filteredSales = new ArrayList<>();
		    for (Miscellaneous miscellaneous : all) {
		        if (miscellaneous.getRegion().equals(domainUser.getRegion_name())) {
		        	filteredSales.add(miscellaneous);
		        }
		    }
		    return filteredSales.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	public List<MiscellaneousDto> getAllMiscCredit(DomainUser domainUser) {
		if (domainUser.getRegion() == null) {
	        domainUser.setRegion("ALL");
	    }
		List<Miscellaneous> all = miscellaneousRepository.findAll();
		
		List<Miscellaneous> credit = new ArrayList<>();
		
		for(Miscellaneous misc : all)
		{
			if(misc.getMoney_type().equals("CREDIT"))
			{
				credit.add(misc);
			}
		}
		
		 if(domainUser.getRegion_name().equals("ALL")) {
			 return credit.stream().map(this::convertToDto).collect(Collectors.toList());
		 }
		 List<Miscellaneous> filteredSales = new ArrayList<>();
		    for (Miscellaneous miscellaneous : credit) {
		        if (miscellaneous.getRegion().equals(domainUser.getRegion_name())) {
		        	filteredSales.add(miscellaneous);
		        }
		    }
		    return filteredSales.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	public List<MiscellaneousDto> getAllMiscDebit(DomainUser domainUser) {
		if (domainUser.getRegion() == null) {
	        domainUser.setRegion("ALL");
	    }
		List<Miscellaneous> all = miscellaneousRepository.findAll();
		
		List<Miscellaneous> debit = new ArrayList<>();
		
		for(Miscellaneous misc : all)
		{
			if(misc.getMoney_type().equals("DEBIT"))
			{
				debit.add(misc);
			}
		}
		
		 if(domainUser.getRegion_name().equals("ALL")) {
			 return debit.stream().map(this::convertToDto).collect(Collectors.toList());
		 }
		 List<Miscellaneous> filteredSales = new ArrayList<>();
		    for (Miscellaneous miscellaneous : debit) {
		        if (miscellaneous.getRegion().equals(domainUser.getRegion_name())) {
		        	filteredSales.add(miscellaneous);
		        }
		    }
		    return filteredSales.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	
	@Override
	public List<Miscellaneous> getAllMisc() {
		return miscellaneousRepository.findAll();
	}
	
	@Override
	public List<MiscellaneousDto> getAllMiscDetail() {
		return getAllMisc().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public void deleteMiscById(String id) {
			Optional<Miscellaneous> byId= miscellaneousRepository.findById(id);
			Miscellaneous miscellaneous = byId.get();
			miscellaneousRepository.delete(miscellaneous);
	}

	@Override
	public List<MiscellaneousDto> getRTOMiscDebit(DomainUser domainUser) {
		List<MiscellaneousDto> miscellaneousDtos = getAllMiscDebit(domainUser);
		List<MiscellaneousDto> RTOMiscDebit = new ArrayList<>();
		
		for(MiscellaneousDto miscellaneousDto: miscellaneousDtos)
		{
//			if(miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Hsrp.toString()) || miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Hypothecation.toString())
//					||	miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Registration.toString()) ||	miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Insurance.toString())
//					||	miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Driving_Licence.toString()))
//			{
//				RTOMiscDebit.add(miscellaneousDto);
//			}
			
		}
		return RTOMiscDebit;	
	}
	
	public List<MiscellaneousDto> getRestMiscDebit(DomainUser domainUser) {
		List<MiscellaneousDto> miscellaneousDtos = getAllMiscDebit(domainUser);
		//List<MiscellaneousDto> RTOMiscDebit = getRTOMiscDebit(domainUser);
//		List<MiscellaneousDto> RestMiscDebit = new ArrayList<>();
//		int flag=0;
//		for(MiscellaneousDto miscellaneousDto: miscellaneousDtos)
//		{
//			for(MiscellaneousDto rtoDto : RTOMiscDebit)
//			{
//				if(miscellaneousDto.getId()== rtoDto.getId())
//				{
//					flag=1;
//				}
//			}
//			if(flag==0)
//			{
//				RestMiscDebit.add(miscellaneousDto);
//			}
//			flag=0;
//		}
		return miscellaneousDtos;
	}

	@Override
	public byte[] downloadSalesData(DomainUser domainUser) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            miscellaneousDataReadExcel.writePurchaseData(workbook,domainUser);
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to download".getBytes(StandardCharsets.UTF_8);
        }
	}
	
	
}
