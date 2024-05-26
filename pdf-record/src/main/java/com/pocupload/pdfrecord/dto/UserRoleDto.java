package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {

	private Integer roleId;

	private String roleType;

	private Boolean roleView;

	private Boolean roleDownload;

	private Boolean roleUpdate;
}
