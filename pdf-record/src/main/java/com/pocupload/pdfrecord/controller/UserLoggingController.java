package com.pocupload.pdfrecord.controller;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserLoggingDto;
import com.pocupload.pdfrecord.model.UserLogging;
import com.pocupload.pdfrecord.service.UserLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-logging")
public class UserLoggingController {

    @Autowired
    private UserLoggingService userLoggingService;

    @PostMapping("/logUserSpecificActions")
    public ResponseEntity<ResponseDto<Boolean>> logUserSpecificActions(@RequestBody UserLoggingDto userLoggingDto) {
        return new ResponseEntity<ResponseDto<Boolean>>(userLoggingService. logUserSpecificActions(userLoggingDto),
                HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
