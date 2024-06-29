package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadDocumentDto {

	private String department;

	private String subArea;

	private String plants;

	private String mainHead;

	private String documentType;

	private Boolean isStatutory;

	private Boolean hodRestricted;

	private Boolean isRestrictedDocument;

}
