package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="TB_E_RIKSHA_VARIANTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERikshaVariants {
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle", nullable = false, foreignKey = @ForeignKey(name = "FK_VEHICLE_E_RIKSHA_VARIANTS"))
    private Vehicle eRiksha;

    @OneToMany(mappedBy = "eRikshaVariants", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ERikshaVariantDetails> eRikshaVariantDetails;

    public ERikshaVariants( String variantName, String color, int price, String features, Vehicle eRiksha) {
        this.variantName = variantName;
        this.color = color;
        this.price = price;
        this.features = features;
        this.eRiksha = eRiksha;
    }
}
