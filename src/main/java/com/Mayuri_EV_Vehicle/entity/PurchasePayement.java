package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PurchasePayement")
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class PurchasePayement {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;

    private int purchasePrice;
    private int totalPrice;

    public PurchasePayement(String id, int purchasePrice, int totalPrice) {
        this.id = id;
        this.purchasePrice = purchasePrice;
        this.totalPrice = totalPrice;
    }
}
