package com.pocupload.pdfrecord.model;

import com.pocupload.pdfrecord.audit.PocEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "is_active", columnDefinition = "boolean default false")
	private Boolean isActive;

	@Column(name = "dir", length = 300)
	private String directory;

	@Column(name = "file_url", length = 300)
	private String fileUrl;

}
