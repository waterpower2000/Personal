package com.Mayuri_EV_Vehicle.entity;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_MISCELLANEOUS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Miscellaneous {
	
	@Id 
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "miscellaneous_id", nullable = false, unique = true)
    private String id;
	
	@Column(name="money_type",nullable = false)
    private String money_type;
	
	@Column(name="date", nullable = false)
    private String date;

//    @Column(name="expense_type", nullable = false)
//    private String expenseType;
	
	@Column(name="amount", nullable = false)
    private String amount;
	
	@Column(name="specification")
    private String specifications;
	
	@Column(name="region")
    private String region;
	
	@Column(name = "created_on")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate createdOn;
	@ManyToOne
	@JoinColumn(name = "miscellanous_heads_id")
	Miscellanous_heads miscellanous_heads;
	public Miscellaneous(String money_type, String date, String amount,
						 String specifications, String region, Miscellanous_heads miscellanous_heads) {
		this.money_type = money_type;
		this.date = date;
//		this.expenseType = expenseType;
		this.amount = amount;
		this.specifications = specifications;
		this.region = region;
		this.createdOn = LocalDate.now();
		this.miscellanous_heads=miscellanous_heads;
	}
	
}
