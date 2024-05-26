package com.pocupload.pdfrecord.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class AuditInfo {

	@CreatedDate
	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@CreatedBy
	@Column(name = "created_by")
	private Integer createdBy;

	@LastModifiedDate
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@LastModifiedBy
	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "deleted_on")
	private LocalDateTime deletedOn;

	@Column(name = "deleted_by")
	private Integer deletedBy;

	@Column(name = "is_deleted")
	private String isDeleted;

}
