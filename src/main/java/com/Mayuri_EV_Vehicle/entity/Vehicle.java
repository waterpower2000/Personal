package com.Mayuri_EV_Vehicle.entity;

import com.Mayuri_EV_Vehicle.model.Region;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity(name = "TB_VEHICLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "vehicle_id", nullable = false, unique = true)
    private String id;

    @Column(name="vehicle_name", nullable = false)
    private String name;

    @Column(name="company_name", nullable = false)
    private String companyName;

    @Column(name="quantity")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false)
    private VechileType vechileType;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<EAuto> eAutoList;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<ERiksha> eRikshaList;

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

    public Vehicle(String name, String companyName, Integer quantity,VechileType vechileType, Region region, String createdBy,String region_name) {
        this.name = name;
        this.companyName = companyName;
        this.quantity = quantity;
        this.vechileType = vechileType;
        this.region = region;
        this.createdBy = createdBy;
        this.createdOn = LocalDateTime.now();
        this.region_name = region_name;

    }
}
