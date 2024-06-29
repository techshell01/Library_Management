package com.pocupload.pdfrecord.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
//import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.pdfbox.Loader;
//import org.apache.pdfbox.multipdf.Splitter;
//import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.dto.UploadDocumentDto;
import com.pocupload.pdfrecord.mapper.DocumentMapper;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.repository.DocumentRepository;
import com.pocupload.pdfrecord.service.DocumentService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DocumentServiceImpl implements DocumentService {

	private final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);

	@Autowired
	HttpServletRequest httpServletRequest;

	@Value("${base.file.upload.dir}")
	String baseDirPath;

	@Value("${config.url.path}")
	String baseUrlPath;

	@Autowired
	DocumentRepository documentRepository;

	@Autowired
	DocumentMapper documentMapper;

	private static final AtomicLong counter = new AtomicLong(1);

	@Override
	public Boolean uploadDocument(MultipartFile multipartFile, String uploadDocument) {
		// TODO Auto-generated method stub

		DocumentInfoDto documentInfoDto = null;

		ObjectMapper objectMapper = new ObjectMapper();
		UploadDocumentDto uploadDocumentDto = new UploadDocumentDto();
		try {
			uploadDocumentDto = objectMapper.readValue(uploadDocument, UploadDocumentDto.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		String appUrl = httpServletRequest.getScheme()
				.concat("://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort());

		List<DocumentInfoDto> listDocumentInfoDtos = new ArrayList<DocumentInfoDto>();

		String requiredFilePath = baseDirPath + uploadDocumentDto.getDepartment();

		String uniqueFileName = String.join(".", uploadDocumentDto.getDepartment(), uploadDocumentDto.getSubArea(),
				uploadDocumentDto.getPlants(), String.format("%04d", counter.getAndIncrement()),
				multipartFile.getOriginalFilename());

		try {

			File isDirectry = new File(requiredFilePath);

			if (!isDirectry.exists()) {
				isDirectry.mkdir();
			}

			File file = convertMultiPartToFile(multipartFile);

			if (Files.exists(Paths.get(requiredFilePath, file.getName()))) {
				return false;
			}

			if (multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."))
					.equalsIgnoreCase(".pdf")) {

				documentInfoDto = new DocumentInfoDto();

				documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
				documentInfoDto.setExtension(multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
				documentInfoDto
						.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setEndIndex(multipartFile.getBytes().length);
				documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
				documentInfoDto.setFileName(multipartFile.getOriginalFilename());
				documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
				documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
				documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
				documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
				documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
				documentInfoDto.setUniqueFileName(uniqueFileName);
				documentInfoDto.setIsActive(Boolean.FALSE);
				documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
				documentInfoDto.setPlant(uploadDocumentDto.getPlants());
				documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
				documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

				String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".pdf");
				FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
				fileOutputStream.write(multipartFile.getBytes());
				fileOutputStream.close();
				listDocumentInfoDtos.add(documentInfoDto);

			} else if (multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".")).equalsIgnoreCase(".docx")) {

				documentInfoDto = new DocumentInfoDto();

				documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
				documentInfoDto.setExtension(multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
				documentInfoDto
						.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setEndIndex(multipartFile.getBytes().length);
				documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
				documentInfoDto.setFileName(multipartFile.getOriginalFilename());
				documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
				documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
				documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
				documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
				documentInfoDto.setUniqueFileName(uniqueFileName);
				documentInfoDto.setIsActive(Boolean.FALSE);
				documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
				documentInfoDto.setPlant(uploadDocumentDto.getPlants());
				documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
				documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

				String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".docx");
				FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
				fileOutputStream.write(multipartFile.getBytes());
				fileOutputStream.close();
				listDocumentInfoDtos.add(documentInfoDto);

			} else if (multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".")).equalsIgnoreCase(".xlsx")) {

				documentInfoDto = new DocumentInfoDto();

				documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
				documentInfoDto.setExtension(multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
				documentInfoDto
						.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setEndIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
				documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
				documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
				documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
				documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
				documentInfoDto.setFileName(uniqueFileName);
				documentInfoDto.setIsActive(Boolean.FALSE);
				documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
				documentInfoDto.setPlant(uploadDocumentDto.getPlants());
				documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
				documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

				String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".xlsx");
				FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
				fileOutputStream.write(multipartFile.getBytes());
				fileOutputStream.close();
				listDocumentInfoDtos.add(documentInfoDto);

			} else if (multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".")).equalsIgnoreCase(".jpeg")) {

				documentInfoDto = new DocumentInfoDto();

				documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
				documentInfoDto.setExtension(multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
				documentInfoDto
						.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setEndIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
				documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
				documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
				documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
				documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
				documentInfoDto.setFileName(uniqueFileName);
				documentInfoDto.setIsActive(Boolean.FALSE);
				documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
				documentInfoDto.setPlant(uploadDocumentDto.getPlants());
				documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
				documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

				String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".jpeg");
				FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
				fileOutputStream.write(multipartFile.getBytes());
				fileOutputStream.close();
				listDocumentInfoDtos.add(documentInfoDto);

			}

			List<DocumentModel> listOfDocumentModel = documentRepository
					.saveAll(documentMapper.mapDocumentInfoDtoListToDocumentModelList(listDocumentInfoDtos));

			if (!listOfDocumentModel.isEmpty()) {
				return true;
			} else {
				return false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
//			logger.error("Error ouccers in Class DocumentServiceImpl in method --> uploadDocument", e.getMessage());
		}

		return false;

	}

	@SuppressWarnings("unused")
	private File convertMultiPartToDocxFile(MultipartFile multipartFile, String requiredFilePath,
			String uniqueFileName) {
		// Ensure the directory exists
		File directory = new File(uniqueFileName);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		// Create the file object in the specified directory with the unique filename
		File file = new File(uniqueFileName + File.separator + uniqueFileName);

		// Use FileOutputStream to write the file
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			FileCopyUtils.copy(multipartFile.getInputStream(), outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
		// TODO Auto-generated method stub
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream outputStream = new FileOutputStream(file);
		FileCopyUtils.copy(multipartFile.getInputStream(), outputStream);
		outputStream.close();
		return file;
	}

	@Override
	public Boolean uploadMultipleDocuments(MultipartFile[] multipartFiles, String uploadDocument) {
		// TODO Auto-generated method stub

		List<DocumentInfoDto> listDocumentInfoDtos = new ArrayList<>();

		String appUrl = httpServletRequest.getScheme()
				.concat("://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort());

		DocumentInfoDto documentInfoDto = null;

		ObjectMapper objectMapper = new ObjectMapper();
		UploadDocumentDto uploadDocumentDto = new UploadDocumentDto();
		try {
			uploadDocumentDto = objectMapper.readValue(uploadDocument, UploadDocumentDto.class);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		}

		String requiredFilePath = baseDirPath + String.join("\\", uploadDocumentDto.getMainHead(),
				uploadDocumentDto.getPlants(), uploadDocumentDto.getSubArea(), uploadDocumentDto.getDepartment());

		String uniqueFileName = null;

		try {

			File isDirectory = new File(requiredFilePath);

			if (!isDirectory.exists()) {
				isDirectory.mkdirs();
			}

			for (MultipartFile multipartFile : multipartFiles) {

				File file = convertMultiPartToFile(multipartFile);

				if (Files.exists(Paths.get(requiredFilePath, file.getName()))) {
					return false; // If any file already exists, return false
				}

				if (multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."))
						.equalsIgnoreCase(".pdf")) {

					uniqueFileName = String.join(".", uploadDocumentDto.getMainHead(),
							uploadDocumentDto.getDepartment(), uploadDocumentDto.getSubArea(),
							uploadDocumentDto.getPlants(), String.format("%04d", counter.getAndIncrement()),
							multipartFile.getOriginalFilename());

					documentInfoDto = new DocumentInfoDto();

					documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
					documentInfoDto.setExtension(multipartFile.getOriginalFilename()
							.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
					documentInfoDto
							.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto.setEndIndex(multipartFile.getBytes().length);
					documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
					documentInfoDto.setFileName(multipartFile.getOriginalFilename());
					documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
					documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
					documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
					documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
					documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
					documentInfoDto.setMainHead(uploadDocumentDto.getMainHead());
					documentInfoDto.setUniqueFileName(uniqueFileName);
					documentInfoDto.setIsActive(Boolean.FALSE);
					documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
					documentInfoDto.setPlant(uploadDocumentDto.getPlants());
					documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
					documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

//					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".pdf");
					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName);
					FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
					fileOutputStream.write(multipartFile.getBytes());
					fileOutputStream.close();
					listDocumentInfoDtos.add(documentInfoDto);

				} else if (multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")).equalsIgnoreCase(".docx")) {

					uniqueFileName = String.join(".", uploadDocumentDto.getMainHead(),
							uploadDocumentDto.getDepartment(), uploadDocumentDto.getSubArea(),
							uploadDocumentDto.getPlants(), String.format("%04d", counter.getAndIncrement()),
							multipartFile.getOriginalFilename());

					documentInfoDto = new DocumentInfoDto();

					documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
					documentInfoDto.setExtension(multipartFile.getOriginalFilename()
							.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
					documentInfoDto
							.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto.setEndIndex(multipartFile.getBytes().length);
					documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
					documentInfoDto.setFileName(multipartFile.getOriginalFilename());
					documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
					documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
					documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
					documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
					documentInfoDto.setUniqueFileName(uniqueFileName);
					documentInfoDto.setIsActive(Boolean.FALSE);
					documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
					documentInfoDto.setMainHead(uploadDocumentDto.getMainHead());
					documentInfoDto.setPlant(uploadDocumentDto.getPlants());
					documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
					documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

//					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".docx");
					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName);
					FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
					fileOutputStream.write(multipartFile.getBytes());
					fileOutputStream.close();
					listDocumentInfoDtos.add(documentInfoDto);

				} else if (multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")).equalsIgnoreCase(".xlsx")) {

					uniqueFileName = String.join(".", uploadDocumentDto.getMainHead(),
							uploadDocumentDto.getDepartment(), uploadDocumentDto.getSubArea(),
							uploadDocumentDto.getPlants(), String.format("%04d", counter.getAndIncrement()),
							multipartFile.getOriginalFilename());

					documentInfoDto = new DocumentInfoDto();

					documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
					documentInfoDto.setExtension(multipartFile.getOriginalFilename()
							.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
					documentInfoDto
							.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto
							.setEndIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
					documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
					documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
					documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
					documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
					documentInfoDto.setFileName(multipartFile.getOriginalFilename());
					documentInfoDto.setUniqueFileName(uniqueFileName);
					documentInfoDto.setIsActive(Boolean.FALSE);
					documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
					documentInfoDto.setMainHead(uploadDocumentDto.getMainHead());
					documentInfoDto.setPlant(uploadDocumentDto.getPlants());
					documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
					documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

//					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".xlsx");
					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName);
					FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
					fileOutputStream.write(multipartFile.getBytes());
					fileOutputStream.close();
					listDocumentInfoDtos.add(documentInfoDto);

				} else if (multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")).equalsIgnoreCase(".jpeg")) {

					uniqueFileName = String.join(".", uploadDocumentDto.getMainHead(),
							uploadDocumentDto.getDepartment(), uploadDocumentDto.getSubArea(),
							uploadDocumentDto.getPlants(), String.format("%04d", counter.getAndIncrement()),
							multipartFile.getOriginalFilename());

					documentInfoDto = new DocumentInfoDto();

					documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
					documentInfoDto.setExtension(multipartFile.getOriginalFilename()
							.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
					documentInfoDto
							.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto
							.setEndIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto.setDirectory(uploadDocumentDto.getDepartment());
					documentInfoDto.setIsHodDocument(uploadDocumentDto.getHodRestricted());
					documentInfoDto.setIsStatutory(uploadDocumentDto.getIsStatutory());
					documentInfoDto.setDocumentType(uploadDocumentDto.getDocumentType());
					documentInfoDto.setIsRestrictedDocument(uploadDocumentDto.getIsRestrictedDocument());
					documentInfoDto.setFileName(multipartFile.getOriginalFilename());
					documentInfoDto.setUniqueFileName(uniqueFileName);
					documentInfoDto.setIsActive(Boolean.FALSE);
					documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
					documentInfoDto.setMainHead(uploadDocumentDto.getMainHead());
					documentInfoDto.setPlant(uploadDocumentDto.getPlants());
					documentInfoDto.setDepartment(uploadDocumentDto.getDepartment());
					documentInfoDto.setSubArea(uploadDocumentDto.getSubArea());

//					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName + ".jpeg");
					String fileNameDir = requiredFilePath.concat("\\" + uniqueFileName);
					FileOutputStream fileOutputStream = new FileOutputStream(fileNameDir);
					fileOutputStream.write(multipartFile.getBytes());
					fileOutputStream.close();
					listDocumentInfoDtos.add(documentInfoDto);

				}
			}
			List<DocumentModel> listOfDocumentModel = documentRepository
					.saveAll(documentMapper.mapDocumentInfoDtoListToDocumentModelList(listDocumentInfoDtos));

			return !listOfDocumentModel.isEmpty();

		} catch (IOException e) {
			e.getStackTrace();
			logger.error("Error occurs in Class DocumentServiceImpl in method --> uploadMultipleDocuments",
					e.getMessage());
		}

		return false;

	}

//	@Override
//	public Boolean uploadMultipleDocuments(MultipartFile[] multipartFiles, String directory) {
//		// TODO Auto-generated method stub
//		
//		List<DocumentInfoDto> listDocumentInfoDtos = new ArrayList<>();
//		
//		String appUrl = httpServletRequest.getScheme()
//				.concat("://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort());
//		
//		String requiredFilePath = baseDirPath + directory;
//		
//		try {
//			
//			File isDirectory = new File(requiredFilePath);
//			
//			if (!isDirectory.exists()) {
//				isDirectory.mkdir();
//			}
//			
//			for (MultipartFile multipartFile : multipartFiles) {
//				
//				File file = convertMultiPartToFile(multipartFile);
//				
//				if (Files.exists(Paths.get(requiredFilePath, file.getName()))) {
//					return false; // If any file already exists, return false
//				}
//				
//				PDDocument document = Loader.loadPDF(file);
//				
//				// Splitter Class
//				Splitter splitting = new Splitter();
//				
//				// Splitting the pages into multiple PDFs
//				List<PDDocument> pages = splitting.split(document);
//				
//				// Using an iterator to traverse all pages
//				Iterator<PDDocument> iteration = pages.listIterator();
//				
//				// Saving each page as an individual document
//				while (iteration.hasNext()) {
//					
//					String uniqueFileName = multipartFile.getOriginalFilename().substring(0,
//							multipartFile.getOriginalFilename().length() - 4) + UUID.randomUUID().toString();
//					
//					DocumentInfoDto documentInfoDto = new DocumentInfoDto();
//					
//					documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
//					documentInfoDto.setExtension(multipartFile.getOriginalFilename()
//							.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
//					documentInfoDto
//					.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
//					documentInfoDto.setEndIndex(document.getNumberOfPages());
//					documentInfoDto.setDirectory(directory);
//					documentInfoDto.setFileName(uniqueFileName);
//					documentInfoDto.setIsActive(Boolean.FALSE);
//					documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));
//					
//					PDDocument pd = iteration.next();
//					
//					pd.save(requiredFilePath + "\\" + uniqueFileName + ".pdf");
//					
//					listDocumentInfoDtos.add(documentInfoDto);
//				}
//				
//				document.close();
//			}
//			
//			List<DocumentModel> listOfDocumentModel = documentRepository
//					.saveAll(documentMapper.mapDocumentInfoDtoListToDocumentModelList(listDocumentInfoDtos));
//			
//			return !listOfDocumentModel.isEmpty();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.getStackTrace();
//			logger.error("Error occurs in Class DocumentServiceImpl in method --> uploadMultipleDocuments",
//					e.getMessage());
//		}
//		
//		return false;
//	}

	@Override
	@Cacheable(value = "fileInfo")
	public List<DocumentInfoDto> getAllFileInfo(String fileName, Pageable pageable) {
		// TODO Auto-generated method stub

		Page<DocumentModel> page = documentRepository.findAll(fileName, pageable);
		List<DocumentInfoDto> listDocumentInfoDtos = documentMapper
				.mapDocumentInfoModelListToDocumentDtoList(page.getContent());

		return listDocumentInfoDtos;
	}

	@Override
	public ResponseEntity<Resource> showFile(String fileName) {
		// TODO Auto-generated method stub

		DocumentModel documentModel = documentRepository.findByFileName(fileName).orElseThrow();

		File dir = new File(
				baseDirPath + documentModel.getDirectory() + "\\" + fileName + documentModel.getExtension());

		try {
			if (dir.exists()) {
				Resource resource = new UrlResource(dir.toURI());

				String contentType = getContentType(documentModel.getExtension());
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
				headers.add(HttpHeaders.CONTENT_TYPE, contentType);
//				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

				return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(contentType))
						.body(resource);
			}
		} catch (Exception e) {
			e.getStackTrace();
			logger.error("Error ouccers in Class DocumentServiceImpl in method --> showFile", e.getMessage());
		}
		return null;
	}

	private String getContentType(String extension) {
		switch (extension.toLowerCase()) {
		case ".pdf":
			return MediaType.APPLICATION_PDF_VALUE;
		case ".txt":
			return MediaType.TEXT_PLAIN_VALUE;
		case ".jpg":
			return MediaType.IMAGE_JPEG_VALUE;
		case ".jpeg":
			return MediaType.IMAGE_JPEG_VALUE;
		case ".png":
			return MediaType.IMAGE_PNG_VALUE;
		case ".xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case ".docx":
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		default:
			return MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

	}

	@Override
	public ResponseEntity<Resource> downloadFile(String fileName) {
		// TODO Auto-generated method stub

		DocumentModel documentModel = documentRepository.findByFileName(fileName).orElseThrow();

		File dir = new File(baseDirPath + documentModel.getDirectory() + "\\" + fileName + ".pdf");

		try {
			if (dir.exists()) {
				Resource resource = new UrlResource(dir.toURI());

				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dir.getName() + "\"");
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
			}
		} catch (Exception e) {
			e.getStackTrace();
			logger.error("Error ouccers in Class DocumentServiceImpl in method --> downloadFile", e.getMessage());
		}
		return null;
	}

}
