package com.Mayuri_EV_Vehicle.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import com.Mayuri_EV_Vehicle.model.Region;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="e_riksha")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ERiksha {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;

    private String name;

    private String companyName;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @Column(nullable = true)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "eRiksha", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ERikshaVariants> eRikshaVariants;
    
    @Enumerated(EnumType.STRING)
    private Region region;
    
    @Column(name="region_name")
    private String region_name;

    public ERiksha(String id, String name, String companyName, Vehicle vehicle,Region region,String region_name) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.vehicle = vehicle;
        this.region=region;
        this.region_name = region_name;
    }
}
