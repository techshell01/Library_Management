package com.pocupload.pdfrecord.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserDto;
import com.pocupload.pdfrecord.mapper.UserMapper;
import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.repository.UserRepository;
import com.pocupload.pdfrecord.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserMapper userMapper;

	@Override
	public ResponseDto<UserDto> getUserByGivenId(Integer userId) {

		ResponseDto<UserDto> response = new ResponseDto<UserDto>();
		UserDto userDto = null;

		try {

			UserDetailsModel userDetail = userRepository.findById(userId).orElse(null);

			if (userDetail != null) {
				userDto = userMapper.mapUserToUserDto(userDetail);
				if (userDto != null) {
					response.setResponse(userDto);
					response.setStatus(HttpStatus.OK.value());
					response.setMessage("success!!");
					return response;
				} else {
					response.setStatus(HttpStatus.NOT_FOUND.value());
					response.setResponse(userDto);
					response.setMessage("Error Ouccers!!");
				}
			} else {
				response.setResponse(userDto);
				response.setStatus(HttpStatus.NOT_FOUND.value());
				response.setMessage("Not Found!!");
				return response;
			}

		} catch (Exception e) {
			e.getStackTrace();
			logger.error("Error ouccers in class UserServiceImpl in method --> getUserByGivenId", e.getMessage());
		}

		return response;
	}

	@Override
	public ResponseDto<List<UserDto>> getAllUsers() {

		ResponseDto<List<UserDto>> response = new ResponseDto<List<UserDto>>();
		List<UserDto> listOfUserDtos = new ArrayList<UserDto>();

		try {

			List<UserDetailsModel> listUserDetailsModels = userRepository.findAll().stream()
					.filter(userDetail -> userDetail.getIsActive() == false).collect(Collectors.toList());

			if (listUserDetailsModels != null && !listUserDetailsModels.isEmpty()) {
				listOfUserDtos = userMapper.mapUserListToUserDtoList(listUserDetailsModels);

				if (listOfUserDtos != null && !listOfUserDtos.isEmpty()) {
					response.setResponse(listOfUserDtos);
					response.setStatus(HttpStatus.OK.value());
					response.setMessage("success!!");
					return response;
				} else {
					response.setResponse(listOfUserDtos);
					response.setStatus(HttpStatus.NO_CONTENT.value());
					response.setMessage("Error Ouccers!!");
				}

			} else {
				response.setResponse(listOfUserDtos);
				response.setStatus(HttpStatus.NOT_FOUND.value());
				response.setMessage("no records found");
			}

		} catch (Exception e) {
			e.getStackTrace();
			logger.error("Error ouccers in class UserServiceImpl in method --> getAllUsers", e.getMessage());
		}
		return response;
	}

	@Override
	public ResponseDto<UserDto> updateUserDetails(UserDto userDto) {

		ResponseDto<UserDto> response = new ResponseDto<UserDto>();
		UserDetailsModel updatedUserDetails = null;
		UserDto updatedUserDto = null;

		try {

			updatedUserDetails = userMapper.mapUserDtoToUserModel(userDto);

			if (updatedUserDetails != null) {
				updatedUserDto = userMapper.mapUserToUserDto(userRepository.save(updatedUserDetails));

				if (updatedUserDto != null) {
					response.setResponse(updatedUserDto);
					response.setMessage("successfully update!!");
					response.setStatus(HttpStatus.OK.value());
					return response;
				} else {
					response.setResponse(updatedUserDto);
					response.setStatus(HttpStatus.NOT_MODIFIED.value());
					response.setMessage("failed to update!!");
				}
			} else {
				response.setMessage("update is not done!!");
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setResponse(updatedUserDto);
			}
		} catch (Exception e) {
			e.getStackTrace();
			logger.error("Error ouccers in class UserServiceImpl in method --> updateUserInfo", e.getMessage());
		}

		return response;
	}

	@Override
	public ResponseDto<Boolean> deleteUserById(Integer userId) {

		ResponseDto<Boolean> response = new ResponseDto<Boolean>();
		UserDetailsModel userDetails = userRepository.findById(userId).orElse(null);

		userDetails.setIsActive(true);

//		update the is_active column 
		userRepository.save(userDetails);

		response.setStatus(HttpStatus.OK.value());
		response.setResponse(true);
		response.setMessage("user delete successfuly!!");

		return response;
	}

	@Override
	public ResponseDto<UserDto> saveUser(UserDto userDto) {
		// TODO Auto-generated method stub

		ResponseDto<UserDto> response = new ResponseDto<>();

		UserDetailsModel userInfo = userRepository.save(userMapper.mapUserDtoToUserModel(userDto));

		response.setMessage("success!!");
		response.setResponse(userMapper.mapUserToUserDto(userInfo));
		response.setStatus(HttpStatus.OK.value());

		return response;
	}

}
