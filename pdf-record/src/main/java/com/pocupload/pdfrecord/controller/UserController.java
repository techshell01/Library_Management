package com.pocupload.pdfrecord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserDto;
import com.pocupload.pdfrecord.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/set-user-info")
	public ResponseEntity<ResponseDto<UserDto>> saveUserInfo(@RequestBody UserDto userDto) {

		return new ResponseEntity<ResponseDto<UserDto>>(userService.saveUser(userDto),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));

	}

	@GetMapping("/get-user-by-id/{userId}")
	public ResponseEntity<ResponseDto<UserDto>> getUserById(@PathVariable("userId") Integer id) {
		return new ResponseEntity<ResponseDto<UserDto>>(userService.getUserByGivenId(id),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@GetMapping("/get-all-user-info")
	public ResponseEntity<ResponseDto<List<UserDto>>> getAllUserInfo() {
		return new ResponseEntity<ResponseDto<List<UserDto>>>(userService.getAllUsers(),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@PutMapping("/update-user-info")
	public ResponseEntity<ResponseDto<UserDto>> updateUserInfo(@RequestBody UserDto userDto) {
		return new ResponseEntity<ResponseDto<UserDto>>(userService.updateUserDetails(userDto),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@DeleteMapping("/delete-user-by-id/{userId}")
	public ResponseEntity<ResponseDto<Boolean>> deleteUserById(@PathVariable("userId") Integer userId) {
		ResponseDto<Boolean> response = userService.deleteUserById(userId);
		if (response.getResponse()) {
			return new ResponseEntity<>(userService.deleteUserById(userId),
					HttpStatusCode.valueOf(HttpStatus.OK.value()));
		} else {
			return new ResponseEntity<ResponseDto<Boolean>>(userService.deleteUserById(userId),
					HttpStatusCode.valueOf(HttpStatus.OK.value()));
		}
	}

}
