package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name="eriksha_variant_details")
@Data
@NoArgsConstructor
public class ERikshaVariantDetails {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "eriksha_variant_details_id")
    private ERikshaVariants eRikshaVariants;

    private String chassis_number;

    private String model_number;
    private String engine_number;
    private String battery_number;
    private String battery_maker_name;
    private String battery_warranty_start_date;
    private String battery_warranty_end_date;
    private String moter_number;
    private String controller_name;
    private String onRoadPrice;

    public ERikshaVariantDetails(String id, ERikshaVariants eRikshaVariants, String chassis_number, String model_number,
                                 String engine_number) {
        this.id = id;
        this.eRikshaVariants = eRikshaVariants;
        this.chassis_number = chassis_number;
        this.model_number = model_number;
        this.engine_number = engine_number;

    }
}
