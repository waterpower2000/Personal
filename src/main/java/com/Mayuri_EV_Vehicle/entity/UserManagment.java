package com.Mayuri_EV_Vehicle.entity;

import com.Mayuri_EV_Vehicle.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="usermanagment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserManagment {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;

    private String userName;
    private String email;
    private String password;
    private String userid;
    private String contactNumber;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id")
    private Region region;

}
