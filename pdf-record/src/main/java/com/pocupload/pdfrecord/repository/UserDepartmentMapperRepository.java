package com.pocupload.pdfrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.UserDepartmentMapper;

@Repository
public interface UserDepartmentMapperRepository extends JpaRepository<UserDepartmentMapper, Integer> {

}
