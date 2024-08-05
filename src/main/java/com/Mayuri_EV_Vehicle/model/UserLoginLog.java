package com.Mayuri_EV_Vehicle.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "TB_USER_LOGIN_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginLog {
	
	 	@Id
	    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
	    @GeneratedValue(generator = "Application-Generic-Generator")
	    @Column(name = "LOGIN_ID", nullable = false, unique = true)
	    private String loginId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "USER_ENTITY", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_LOGIN_LOG_USER"))
	    private UserEntity userEntity;

	    @Column(name = "LOGIN_TIME", nullable = false)
	    private LocalDateTime loginTime;

	    @Column(name = "LOGIN_TERMINAL", nullable = false)
	    private String loginTerminal;

	    @Lob
	    @Column(name = "LOGIN_USER_AGENT")
	    private String userAgent;

	    @Column(name = "LOGOUT_TIME")
	    private LocalDateTime logoutTime;

}
