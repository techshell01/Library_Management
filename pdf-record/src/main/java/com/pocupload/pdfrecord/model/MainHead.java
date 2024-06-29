package com.pocupload.pdfrecord.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "main_heads")
public class MainHead {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "head_id")
	private Integer headId;

	@Column(name = "head_name")
	private String headName;

}
