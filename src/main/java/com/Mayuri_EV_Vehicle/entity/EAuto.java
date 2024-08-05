package com.Mayuri_EV_Vehicle.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import com.Mayuri_EV_Vehicle.model.Region;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="e_auto")
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EAuto {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;
    private String name;
    private String companyName;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @Column(nullable = true)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToMany(mappedBy = "eAuto", cascade = CascadeType.ALL)
    private List<EAutoVariants> eAutoVariants;
    
    @Enumerated(EnumType.STRING)
    private Region region;
    
    @Column(name="region_name")
    private String region_name;


	public EAuto(String id, String name, String companyName, Vehicle vehicle, Region region,String region_name) {
		this.id=id;
      this.name = name;
      this.companyName = companyName;
      this.vehicle = vehicle;
      this.region=region;
      this.region_name=region_name;
	}
}
