package com.pocupload.pdfrecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsModel, Integer> {

	List<UserDetailsModel> findByRole(UserRole role);

}
