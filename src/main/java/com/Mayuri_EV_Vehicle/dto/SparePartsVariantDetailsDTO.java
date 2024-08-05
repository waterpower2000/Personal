package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.SpareParts;
import com.Mayuri_EV_Vehicle.model.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SparePartsVariantDetailsDTO {

    private String id;
    private String sparePartsId;
    private String sparePartsName;
    private String companyName;
    private String chassis_number;
    private String model_number;
    private String region;
}
