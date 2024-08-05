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

import org.hibernate.annotations.GenericGenerator;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "TB_USER_ROLE_MAPPING")
@Getter
@Setter
@NoArgsConstructor
public class UserRoleMapping {
	
	@Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    @Column(name = "USER_ROLE_MAPPING_ID", nullable = false, unique = true)
    private String userRoleMappingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ENTITY", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE_MAPPING_USER"))
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREATED_BY", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE_MAPPING_CREATED_BY"))
    private UserEntity createdBy;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    public UserRoleMapping(UserEntity userEntity, UserRole userRole, UserEntity createdBy) {
        this.userEntity = userEntity;
        this.userRole = userRole;
        this.createdBy = createdBy;
        this.createdOn = LocalDateTime.now();
    }

}
