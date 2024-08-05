package com.Mayuri_EV_Vehicle.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "TB_REGION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionTable {
	
	@Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "REGION_CODE", nullable = false, unique = true)
    private String regionId;
	
	@Column(name = "REGION_NAME", nullable = false)
    private String regionName;
	
	@Lob
	@Column(name = "REGION_DETAILS", nullable = false)
    private String regionDetails;
	
//	@OneToMany(mappedBy = "regionTable", cascade = CascadeType.ALL)
//    private List<UserEntity> users;
	
	public RegionTable(String regionName,String regionDetails)
	{
		this.regionName = regionName;
		this.regionDetails = regionDetails;
	}
	
}
