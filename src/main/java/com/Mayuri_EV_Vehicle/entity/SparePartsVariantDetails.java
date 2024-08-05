package com.Mayuri_EV_Vehicle.entity;

import com.Mayuri_EV_Vehicle.model.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TB_SPARE_PARTS_VARIANTS_DETAILS")
@Setter
@Getter
@NoArgsConstructor
public class SparePartsVariantDetails {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "spare_parts_variant_details_id", nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spare_parts", nullable = false, foreignKey = @ForeignKey(name = "FK_TB_SPARE_PARTS_SPARE_PARTS_VARIANTS_DETAILS"))
    private SpareParts spareParts;

    @Column(name="chassis_number")
    private String chassis_number;

    @Column(name="model_number")
    private String model_number;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;
    
    @Column(name="region_name")
    private String region_name;

    public SparePartsVariantDetails(SpareParts spareParts, String chassis_number, String model_number, Region region, String region_name){
        this.spareParts = spareParts;
        this.chassis_number = chassis_number;
        this.model_number = model_number;
        this.region = region;
        this.region_name = region_name;
    }
}
