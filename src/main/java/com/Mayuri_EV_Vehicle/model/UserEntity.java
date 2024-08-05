package com.Mayuri_EV_Vehicle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "TB_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	@Id
    @Column(name = "USER_CODE", nullable = false, unique = true)
    private String userCode;

    @Column(name = "USER_PASS", nullable = false)
    private String userPass;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "IS_ENABLED")
    private boolean enabled;
    
   @Enumerated(EnumType.STRING)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REGION_CODE", nullable = false , foreignKey = @ForeignKey(name = "FK_REGION_CODE_USER"))
    private RegionTable regionTable;
    
//    public UserEntity(String userCode, String userPass,String firstName,String lastName,boolean enabled,Region region,RegionTable regionTable)
//    {
//    	this.userCode = userCode;
//    	this.userPass = userPass;
//    	this.firstName = firstName;
//    	this.lastName = lastName;
//    	this.enabled = enabled;
//    	this.region = region;
//    	this.regionTable = regionTable;
//    }
}
