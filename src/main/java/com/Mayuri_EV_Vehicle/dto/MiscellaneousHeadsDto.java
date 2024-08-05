package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiscellaneousHeadsDto {
    private String id;
    private String account_type;
    private String transaction_name;
    private String region;
}
