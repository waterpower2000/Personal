package com.Mayuri_EV_Vehicle.entity;

import com.Mayuri_EV_Vehicle.model.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_SPARE_PARTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpareParts {

    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "spare_parts_id", nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company_name")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime updatedOn;
    
    @Column(name="region_name")
    private String region_name;

    public SpareParts(String name, String companyName, Region region, String createdBy,String region_name){
        this.name = name;
        this.companyName = companyName;
        this.region = region;
        this.quantity = 0;
        this.createdBy = createdBy;
        this.createdOn = LocalDateTime.now();
        this.region_name = region_name;
    }
}
