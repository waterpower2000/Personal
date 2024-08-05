package com.Mayuri_EV_Vehicle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SalesPayement")
@Data
@NoArgsConstructor
public class SalesPayment {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;

    private int salesPrice;
    private int totalPrice;

    public SalesPayment(String id, int salesPrice, int totalPrice) {
        this.id = id;
        this.salesPrice = salesPrice;
        this.totalPrice = totalPrice;
    }
}
