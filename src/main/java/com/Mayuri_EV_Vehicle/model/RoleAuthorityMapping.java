package com.Mayuri_EV_Vehicle.model;

import java.time.LocalDateTime;

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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_ROLE_AUTHORITY_MAPPING")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuthorityMapping {
	
	@Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "ROLE_AUTHORITY_MAPPING_ID", nullable = false, unique = true)
    private String roleAuthorityMappingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_CODE", nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTHORITY", nullable = false)
    private Authority authority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREATED_BY", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_AUTHORITY_MAPPING_USER"))
    private UserEntity createdBy;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    public RoleAuthorityMapping(UserRole userRole, Authority authority, UserEntity createdBy) {
    	
        this.userRole = userRole;
        this.authority = authority;
        this.createdBy = createdBy;
    }

}
