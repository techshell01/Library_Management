package com.pocupload.pdfrecord.repository;

import com.pocupload.pdfrecord.model.UserLogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoggingRepository extends JpaRepository<UserLogging, Long> {
}
