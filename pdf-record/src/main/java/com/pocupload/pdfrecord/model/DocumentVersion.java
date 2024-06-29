package com.pocupload.pdfrecord.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_version")
public class DocumentVersion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "version_id")
	private Integer versionId;

	@Column(name = "version_name", length = 5)
	private String versionName;

	@Column(name = "version_release_date")
	private Date versionReleaseDate;

	@ManyToOne
	@JoinColumn(name = "ref_id", referencedColumnName = "ref_id")
	private DocumentModel documenId;

}
