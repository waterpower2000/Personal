package com.Mayuri_EV_Vehicle.dto;


import com.Mayuri_EV_Vehicle.model.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SparePartsDTO {

    private String id;
    private String name;
    private String companyName;
    private String region;
    private Integer quantity;
    private String createdBy;
    private LocalDateTime createdOn;
    private String updatedBy;
    private LocalDateTime updatedOn;
}
