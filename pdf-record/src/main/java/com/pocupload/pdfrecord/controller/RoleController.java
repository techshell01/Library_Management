package com.pocupload.pdfrecord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserRoleDto;
import com.pocupload.pdfrecord.service.UserRoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	UserRoleService userRoleService;

	@PostMapping("/save-role")
	public ResponseEntity<ResponseDto<Boolean>> saveRoleUser(@RequestBody UserRoleDto userRoleDto) {
		return new ResponseEntity<ResponseDto<Boolean>>(userRoleService.saveRoleUsers(userRoleDto),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));

	}

	@GetMapping("/get-user-role-detail")
	public ResponseEntity<ResponseDto<List<UserRoleDto>>> getAllUserInfo() {
		return new ResponseEntity<ResponseDto<List<UserRoleDto>>>(userRoleService.getAllUserRole(),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));

	}

}
