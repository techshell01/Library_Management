package com.pocupload.pdfrecord.model;

import com.pocupload.pdfrecord.audit.PocEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
@EntityListeners(PocEntityListener.class)
public class UserDetailsModel extends AuditInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name", length = 50)
	private String userName;

	@Column(name = "phone_number")
	private Integer phoneNumber;

	@Column(name = "password")
	private String password;

	@Column(name = "email_id", length = 50)
	private String emailId;

	@Column(name = "is_active")
	private Boolean isActive;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private UserRole role;

}
