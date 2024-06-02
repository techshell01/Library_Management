package com.pocupload.pdfrecord.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserRoleDto;
import com.pocupload.pdfrecord.mapper.UserMapper;
import com.pocupload.pdfrecord.mapper.UserRoleMapper;
import com.pocupload.pdfrecord.model.UserRole;
import com.pocupload.pdfrecord.repository.UserRepository;
import com.pocupload.pdfrecord.repository.UserRoleRepository;
import com.pocupload.pdfrecord.service.UserRoleService;
import com.pocupload.pdfrecord.utils.ApplicationUtils;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRoleMapper userRoleMapper;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseDto<List<UserRoleDto>> getAllUserRole() {
		// TODO Auto-generated method stub

		ResponseDto<List<UserRoleDto>> responseDto = new ResponseDto<>();

		List<UserRole> listUserRoles = userRoleRepository.findAll().stream()
				.filter(userRole -> !userRole.getRoleType().equalsIgnoreCase(ApplicationUtils.ADMIN))
				.collect(Collectors.toList());

		responseDto.setMessage("success!!");
		responseDto.setResponse(userRoleMapper.mapRoleModelListToRoleDtoList(listUserRoles));
		responseDto.setStatus(HttpStatus.OK.value());

		return responseDto;
	}

	@Override
	public ResponseDto<Boolean> saveRoleUsers(UserRoleDto userRoleDto) {
		// TODO Auto-generated method stub

		ResponseDto<Boolean> response = new ResponseDto<Boolean>();

		UserRole userRole = userRoleRepository.save(userRoleMapper.mapRoleUserDtoToRoleUser(userRoleDto));

		if (userRole != null) {

			response.setMessage("success!!");
			response.setStatus(HttpStatus.OK.value());
			response.setResponse(Boolean.TRUE);

			return response;
		}

		response.setMessage("Error To Save Role!!");
		response.setResponse(Boolean.FALSE);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return response;
	}

}
