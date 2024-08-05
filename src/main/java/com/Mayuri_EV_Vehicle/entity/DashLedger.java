package com.Mayuri_EV_Vehicle.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="dashLedger")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashLedger {
	@Id
    private String id;
	private String type;
	private String date;
	private Double totalAmount;
}
