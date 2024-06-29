package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWiseDocumentDto {

	private String departmentName;

	private Integer doucmentCount;

}
