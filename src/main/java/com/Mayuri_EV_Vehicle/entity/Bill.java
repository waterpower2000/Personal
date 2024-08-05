package com.Mayuri_EV_Vehicle.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    private String id;
    private String billNumber;
    private double amount;
    private Date billDate;

}
