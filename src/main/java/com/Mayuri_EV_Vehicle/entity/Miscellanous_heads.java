package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="TB_MISCELLANEOUS_HEADS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Miscellanous_heads {
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "other_misc_id", nullable = false, unique = true)
    private String id;
    @Column(name="account_type",nullable = false)
    private String account_type;
    @Column(name="transaction_name",nullable = false, unique = true)
    private String transaction_name;
    @Column(name="region")
    private String region;
    public Miscellanous_heads(String account_type,String transaction_name,String region)
    {
        this.account_type=account_type;
        this.transaction_name=transaction_name;
        this.region = region;
    }

}
