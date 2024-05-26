package com.pocupload.pdfrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:constants.properties")
public class PdfRecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfRecordApplication.class, args);
	}

}
