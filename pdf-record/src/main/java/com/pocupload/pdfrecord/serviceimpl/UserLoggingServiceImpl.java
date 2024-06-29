package com.pocupload.pdfrecord.serviceimpl;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserLoggingDto;
import com.pocupload.pdfrecord.model.Department;
import com.pocupload.pdfrecord.model.UserLogging;
import com.pocupload.pdfrecord.model.UserRole;
import com.pocupload.pdfrecord.repository.DepartmentRepository;
import com.pocupload.pdfrecord.repository.UserLoggingRepository;
import com.pocupload.pdfrecord.repository.UserRoleRepository;
import com.pocupload.pdfrecord.service.UserLoggingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoggingServiceImpl implements UserLoggingService {

    private final Logger logger = LogManager.getLogger(UserLoggingServiceImpl.class);

    @Autowired
    private UserLoggingRepository userLoggingRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public ResponseDto<Boolean> logUserSpecificActions(UserLoggingDto userLoggingDto) {
        ResponseDto<Boolean> response = new ResponseDto<>();
        try {
            UserLogging userLogging = new UserLogging();
            prepareUserActionLogging(userLoggingDto, userLogging);
            userLoggingRepository.save(userLogging);
            response.setMessage("Logged..!!");
            response.setStatus(HttpStatus.OK.value());
            response.setResponse(Boolean.TRUE);
        } catch (Exception e) {
            logger.error("Error occurs while log the user actions",
                    e.getMessage());
            e.printStackTrace();
            response.setMessage("Logging Failed..!!");
            response.setResponse(Boolean.FALSE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    private void prepareUserActionLogging(UserLoggingDto userLoggingDto, UserLogging userLogging) {
        userLogging.setUserName(userLoggingDto.getUserName());
        userLogging.setAction(userLoggingDto.getAction());
        Optional<Department> department = departmentRepository.findById(userLoggingDto.getDepartmentId());
        department.ifPresent(userLogging::setDepartment);
        Optional<UserRole> role = userRoleRepository.findById(userLoggingDto.getRoleId());
        role.ifPresent(userLogging::setUserRole);
    }
}