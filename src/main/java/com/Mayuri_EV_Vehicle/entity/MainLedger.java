package com.Mayuri_EV_Vehicle.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="mainLedger")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainLedger {
	
	@Id
    private String id;
	private String type;
	private String date;
	private Double totalAmount;
	
}
