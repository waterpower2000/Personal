package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateMiscellaneousHeads;
import com.Mayuri_EV_Vehicle.dto.MiscellaneousHeadsDto;

import java.util.List;

public interface MiscellaneousHeadsService {
    MiscellaneousHeadsDto saveMiscellaneousHeads(CreateMiscellaneousHeads createMiscellaneousHeadsDto, DomainUser domainUser);
    List<MiscellaneousHeadsDto>findallheads();

}
