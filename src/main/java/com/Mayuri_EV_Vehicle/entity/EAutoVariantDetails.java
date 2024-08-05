package com.Mayuri_EV_Vehicle.entity;

import com.Mayuri_EV_Vehicle.model.Region;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TB_E_AUTO_VARIANTS_DETAILS")
@Setter
@Getter
@NoArgsConstructor
public class EAutoVariantDetails{
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "e_auto_variant_details_id", nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle", nullable = false, foreignKey = @ForeignKey(name = "FK_E_AUTO_VARIANTS_E_AUTO_VARIANTS_DETAILS"))
    private EAutoVariants eAutoVariants;

    @Column(name="chassis_number")
    private String chassis_number;

    @Column(name="model_number")
    private String model_number;

    @Column(name="engine_number")
    private String engine_number;

    @Column(name="battery_number")
    private String battery_number;

    @Column(name="battery_maker_name")
    private String battery_maker_name;

    @Column(name="battery_warranty_start_date")
    private String battery_warranty_start_date;

    @Column(name="battery_warranty_end_date")
    private String battery_warranty_end_date;

    @Column(name="moter_number")
    private String moter_number;

    @Column(name="controller_name")
    private String controller_name;

    @Column(name="onRoadPrice")
    private String onRoadPrice;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;
    
    @Column(name="region_name")
    private String region_name;

    public EAutoVariantDetails(EAutoVariants eAutoVariants, String chassis_number, String model_number, String engine_number, String battery_number, String battery_maker_name, String battery_warranty_start_date,
                               String battery_warranty_end_date, String moter_number, String controller_name, String onRoadPrice, Region region,String region_name) {
        this.eAutoVariants = eAutoVariants;
        this.chassis_number = chassis_number;
        this.model_number = model_number;
        this.engine_number = engine_number;
        this.battery_number = battery_number;
        this.battery_maker_name = battery_maker_name;
        this.battery_warranty_start_date = battery_warranty_start_date;
        this.battery_warranty_end_date = battery_warranty_end_date;
        this.moter_number = moter_number;
        this.controller_name = controller_name;
        this.onRoadPrice = onRoadPrice;
        this.region = region;
        this.region_name = region_name;
    }
}
