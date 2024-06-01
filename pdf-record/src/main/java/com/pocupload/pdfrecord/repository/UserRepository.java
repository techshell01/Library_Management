package com.pocupload.pdfrecord.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsModel, Integer> {

	List<UserDetailsModel> findByRole(UserRole role);

	@Query("select udm from UserDetailsModel udm where udm.userName = :userName AND udm.password = :password")
	UserDetailsModel getUserInfoByUserNameAndPassword(@Param("userName") String userName,
			@Param("password") String password);

}
