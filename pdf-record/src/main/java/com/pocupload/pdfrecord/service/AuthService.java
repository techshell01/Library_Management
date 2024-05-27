package com.pocupload.pdfrecord.service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.LoginCredDto;
import com.pocupload.pdfrecord.dto.LoginCredResponseDto;

public interface AuthService {

	public ResponseDto<LoginCredResponseDto> userLogin(LoginCredDto loginCredDto);

}
