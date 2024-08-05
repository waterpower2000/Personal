package com.Mayuri_EV_Vehicle.entity;

import javax.persistence.Entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="inventoryDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDetails {
	@Id
	private String Id;
	@Column(nullable=true)
	private String Customer_Suppiler_Name;
	@Column(nullable=true)
	private String category;
	@Column(nullable=true)
	private String subCategory;
	@Column(nullable=true)
	private String variants;
	@Column(nullable=true)
	private String transactionType;
	 	 
}
