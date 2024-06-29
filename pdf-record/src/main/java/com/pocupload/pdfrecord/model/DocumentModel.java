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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_information")
@EntityListeners(PocEntityListener.class)
public class DocumentModel extends AuditInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ref_id")
	private Integer refernceId;

	@Column(name = "file_size")
	private String fileSize;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "start_indx")
	private Integer startIndex;

	@Column(name = "end_indx")
	private Integer endIndex;

	@Column(name = "extension")
	private String extension;

	@Column(name = "is_active", columnDefinition = "boolean default true")
	private Boolean isActive;

	@Column(name = "dir", length = 300)
	private String directory;

	@Column(name = "file_url", length = 300)
	private String fileUrl;

	@Column(name = "document_type", length = 25)
	private String documentType;

	@Column(name = "statutory_document")
	private Boolean isStatutory;

	@Column(name = "restricted_document")
	private Boolean isRestrictedDocument;

	@Column(name = "hod_document")
	private Boolean isHodDocument;

	@Column(name = "unique_file_name")
	private String uniqueFileName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", referencedColumnName = "department_id")
	private Department department;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_area_id", referencedColumnName = "sub_area_id")
	private SubArea subArea;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plant_id", referencedColumnName = "plant_id")
	private Plants plant;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "head_id", referencedColumnName = "head_id")
	private MainHead mainHead;

}
