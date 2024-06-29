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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plant")
@EntityListeners(PocEntityListener.class)
public class Plants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plant_id")
	private Integer plantId;

	@Column(name = "plant_name")
	private String plantName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "head_id", referencedColumnName = "head_id")
	private MainHead mainHead;

}
