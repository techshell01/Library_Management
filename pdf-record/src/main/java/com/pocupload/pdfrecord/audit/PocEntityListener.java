package com.pocupload.pdfrecord.audit;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.pocupload.pdfrecord.model.AuditInfo;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Component
public class PocEntityListener {

	@PrePersist
	@PreUpdate
	public void preInsert(AuditInfo entity) {

		entity.setCreatedBy(1);
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedBy(1);
		entity.setUpdatedOn(LocalDateTime.now());
		entity.setIsDeleted("N");
		entity.setDeletedOn(LocalDateTime.now());
		entity.setDeletedBy(1);

	}

//	@PreUpdate
//	public void preUpdate(AuditInfo entity) {
//		entity.setUpdatedBy(1);
//		entity.setUpdatedOn(LocalDateTime.now());
//	}

}
