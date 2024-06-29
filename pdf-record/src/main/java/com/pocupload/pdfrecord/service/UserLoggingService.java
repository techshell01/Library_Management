package com.pocupload.pdfrecord.service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserLoggingDto;

public interface UserLoggingService {
    ResponseDto<Boolean>  logUserSpecificActions(UserLoggingDto userLoggingDto);
}
