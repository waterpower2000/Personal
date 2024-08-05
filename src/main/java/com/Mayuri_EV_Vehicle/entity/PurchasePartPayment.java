package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="TB_PURCHASE_PART_PAYMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePartPayment {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "purchase_payment_id", nullable = false, unique = true)
    private String id;

    @Column(name = "created_on")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate createdOn;

    @Column(name="purchase_paid_amount")
    private Double perQuantityPrice;

    @Column(name="purchase_id")
    private String purchase_id;

    @ManyToOne
    @JoinColumn(name = "e_auto_variant_details_id")
    Purchase purchase;

    public PurchasePartPayment(Double perQuantityPrice,String purchase_id,Purchase purchase)
    {
        this.perQuantityPrice=perQuantityPrice;
        this.createdOn = LocalDate.now();
        this.purchase_id=purchase_id;
        this.purchase=purchase;
    }

}
