package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Integer userId;

	private String userName;

	private String phoneNumber;

	private String emailId;

	private String password;

	private Boolean isActive;

	private String role;

}
