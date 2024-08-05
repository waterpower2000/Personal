package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateMiscellaneousHeads;
import com.Mayuri_EV_Vehicle.dto.MiscellaneousHeadsDto;
import com.Mayuri_EV_Vehicle.entity.Miscellanous_heads;
import com.Mayuri_EV_Vehicle.repository.MiscellaneousHeadsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class MiscellaneousHeadsServiceImpl implements MiscellaneousHeadsService{

    MiscellaneousHeadsRepository miscellaneousHeadsRepository;
    public MiscellaneousHeadsServiceImpl(MiscellaneousHeadsRepository miscellaneousHeadsRepository)
    {
       this.miscellaneousHeadsRepository=miscellaneousHeadsRepository;
    }

    @Override
    public MiscellaneousHeadsDto saveMiscellaneousHeads(CreateMiscellaneousHeads createMiscellaneousHeadsDto, DomainUser domainUser) {
        Miscellanous_heads Miscellanous_heads=null;
        createMiscellaneousHeadsDto.setTransaction_name(createMiscellaneousHeadsDto.getTransaction_name().toUpperCase());
        List<MiscellaneousHeadsDto> list=findallheads();
        boolean temp=true;
        for(MiscellaneousHeadsDto miscellaneousHeadsDto: list)
        {
            if(miscellaneousHeadsDto.getTransaction_name().equals(createMiscellaneousHeadsDto.getTransaction_name()))
            {
                temp=false;
            }
        }
        if(temp)
        {
            Miscellanous_heads=new Miscellanous_heads(createMiscellaneousHeadsDto.getAccount_type(),createMiscellaneousHeadsDto.getTransaction_name(),domainUser.getRegion_name());
            Miscellanous_heads save=miscellaneousHeadsRepository.save(Miscellanous_heads);
            return convertToDto(save);
        }
        else {
            Miscellanous_heads save=null;
            return convertToDto(save);
        }



    }

    @Override
    public List<MiscellaneousHeadsDto> findallheads() {
        List<Miscellanous_heads> list=miscellaneousHeadsRepository.findAll();
        List<MiscellaneousHeadsDto> headslist=new ArrayList<>();
        for(Miscellanous_heads miscellanous_heads:list)
        {
            headslist.add(convertToDto(miscellanous_heads));
        }
        return headslist;
    }


    public MiscellaneousHeadsDto convertToDto(Miscellanous_heads Miscellanous_heads)
    {
        if(Miscellanous_heads==null)
        {
            return null;
        }
        else {
            MiscellaneousHeadsDto miscellaneousHeadsDto = new MiscellaneousHeadsDto(Miscellanous_heads.getId(), Miscellanous_heads.getAccount_type(), Miscellanous_heads.getTransaction_name(), Miscellanous_heads.getRegion());
            return miscellaneousHeadsDto;
        }
    }
}
