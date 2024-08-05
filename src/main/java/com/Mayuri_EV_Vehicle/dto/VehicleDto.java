package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.Type;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    private String vehicleId;
    private String name;
    private String companyName;
    private Integer quantity;
    private String type;
    private String region;
    private String createdBy;
    private LocalDateTime createdOn;
    private String updatedBy;
    private LocalDateTime updatedOn;


}
