package com.pocupload.pdfrecord.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.LoginCredDto;
import com.pocupload.pdfrecord.dto.LoginCredResponseDto;
import com.pocupload.pdfrecord.mapper.UserAuthMapperImpl;
import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.repository.UserRepository;
import com.pocupload.pdfrecord.service.AuthService;
import com.pocupload.pdfrecord.utils.PasswordUtils;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAuthMapperImpl userAuthMapperImpl;

	@Override
	public ResponseDto<LoginCredResponseDto> userLogin(LoginCredDto loginCredDto) {

		ResponseDto<LoginCredResponseDto> response = new ResponseDto<LoginCredResponseDto>();

		if (loginCredDto.getUserName() == null || loginCredDto.getPassword() == null) {
			response.setMessage("Bad Request");
			response.setResponse(null);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return response;
		}

		UserDetailsModel userDetailsModel = userRepository.getUserInfoByUserNameAndPassword(loginCredDto.getUserName(),
				PasswordUtils.hashPassword(loginCredDto.getPassword()));

		if (userDetailsModel != null) {
			if (userDetailsModel.getIsActive()) {
				LoginCredResponseDto loginCredResponseDto = userAuthMapperImpl
						.mapUserCredToUserDetailsModel(userDetailsModel);

				if (loginCredResponseDto != null) {
					response.setStatus(HttpStatus.OK.value());
					response.setResponse(loginCredResponseDto);
					response.setMessage("success!!");
					return response;
				} else {
					response.setMessage("Access Denied");
					response.setResponse(loginCredResponseDto);
					response.setStatus(HttpStatus.FORBIDDEN.value());
					return response;
				}
			} else {
				response.setMessage("Sorry Your Account Has Been Blocked Contact To Authority");
				response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
				response.setResponse(null);
			}
		} else {

			response.setMessage("no record found");
			response.setStatus(HttpStatus.OK.value());
			response.setResponse(null);
		}

		return response;
	}

}
