package com.Mayuri_EV_Vehicle.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="dashboard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoard {
	
	@Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
	private String id;
	private String barFromDate;
	private String barToDate;
	private String led_cat;
	private String piAsOnDate;
	private String ledgerFromDate;
	private String ledgerToDate;
	private String region;
	
	
}
