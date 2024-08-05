package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;
    private String supplier_name;
    private String supplier_number;
    private String supplier_AadhaarCard;
    private String supplier_PanCard;
    private String supplier_GSTNumber;
    @Lob
    private String supplier_address;
}
