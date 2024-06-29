package com.pocupload.pdfrecord.model;

import com.pocupload.pdfrecord.audit.PocEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department_master")
@EntityListeners(PocEntityListener.class)
public class Department extends AuditInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Integer departmentId;

	@Column(name = "department_name")
	private String departmentName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plant_id", referencedColumnName = "plant_id")
	private Plants plant;
}
