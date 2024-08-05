package com.Mayuri_EV_Vehicle.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="region")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {
	@Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;
	
	private String regionName;
	private String regionDetails;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<UserManagment> users;

    public Region(String id, String regionName, String regionDetails) {
        this.id = id;
        this.regionName = regionName;
        this.regionDetails = regionDetails;
    }
}
