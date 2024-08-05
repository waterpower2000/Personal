package com.Mayuri_EV_Vehicle.entity;

import com.Mayuri_EV_Vehicle.model.Region;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TB_E_AUTO_VARIANTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EAutoVariants {

    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "e_auto_variant_id", nullable = false, unique = true)
    private String id;
    @Column(name="variant_name", nullable = false)
    private String variantName;
    @Column(name="colour", nullable = false)
    private String color;
    @Column(name="price")
    private int price;
    @Column(name="features")
    private String features;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle", nullable = false, foreignKey = @ForeignKey(name = "FK_VEHICLE_E_AUTO_VARIANTS"))
    private Vehicle eAuto;

    @OneToMany(mappedBy = "eAutoVariants", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EAutoVariantDetails> eAutoVariantDetails;
    
    @Column(name="region_name")
    private String region_name;

    public EAutoVariants(String variantName,String color, int price, String features, Region region ,Vehicle eAuto, String region_name) {
        this.variantName=variantName;
        this.color = color;
        this.price = price;
        this.features = features;
        this.region = region;
        this.eAuto = eAuto;
        this.region_name = region_name;
    }
}