package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentInfoDto {

	private Integer refernceId;

	private String fileSize;

	private String fileName;

	private String extension;

	private Integer startIndex;

	private Integer endIndex;

	private String documentType;

	private Boolean isActive;

	private Boolean isHodDocument;

	private Boolean isStatutory;

	private Boolean isRestrictedDocument;

	private String uniqueFileName;

	private String directory;

	private String department;

	private String subArea;

	private String plant;

	private String mainHead;

	private String fileUrl;

}
