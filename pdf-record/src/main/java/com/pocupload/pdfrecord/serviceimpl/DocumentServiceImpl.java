package com.pocupload.pdfrecord.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
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

	@Override
	public Boolean uploadDocument(MultipartFile multipartFile, String directory) {
		// TODO Auto-generated method stub

		DocumentInfoDto documentInfoDto = null;

		String appUrl = httpServletRequest.getScheme()
				.concat("://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort());

		List<DocumentInfoDto> listDocumentInfoDtos = new ArrayList<DocumentInfoDto>();

		String requiredFilePath = baseDirPath + directory;

		try {

			File isDirectry = new File(requiredFilePath);

			if (!isDirectry.exists()) {
				isDirectry.mkdir();
			}

			File file = convertMultiPartToFile(multipartFile);

			if (Files.exists(Paths.get(requiredFilePath, file.getName()))) {
				return false;
			}

			PDDocument document = Loader.loadPDF(file);

			// Splitter Class
			Splitter splitting = new Splitter();

			// Splitting the pages into multiple PDFs
			List<PDDocument> Page = splitting.split(document);

			// Using a iterator to Traverse all pages
			Iterator<PDDocument> iteration = Page.listIterator();

			// Saving each page as an individual document
			while (iteration.hasNext()) {

				String uniqueFileName = multipartFile.getOriginalFilename().substring(0,
						multipartFile.getOriginalFilename().length() - 4) + UUID.randomUUID().toString();

				documentInfoDto = new DocumentInfoDto();

				documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
				documentInfoDto.setExtension(multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
				documentInfoDto
						.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
				documentInfoDto.setEndIndex(document.getNumberOfPages());
				documentInfoDto.setDirectory(directory);
				documentInfoDto.setFileName(uniqueFileName);
				documentInfoDto.setIsActive(Boolean.FALSE);
				documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));

				PDDocument pd = iteration.next();

				pd.save(requiredFilePath + "\\" + uniqueFileName + ".pdf");

				listDocumentInfoDtos.add(documentInfoDto);

			}

			List<DocumentModel> listOfDocumentModel = documentRepository
					.saveAll(documentMapper.mapDocumentInfoDtoListToDocumentModelList(listDocumentInfoDtos));

			document.close();

			if (!listOfDocumentModel.isEmpty()) {
				return true;
			} else {
				return false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
			logger.error("Error ouccers in Class DocumentServiceImpl in method --> uploadDocument", e.getMessage());
		}

		return false;

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
	public Boolean uploadMultipleDocuments(MultipartFile[] multipartFiles, String directory) {
		// TODO Auto-generated method stub

		List<DocumentInfoDto> listDocumentInfoDtos = new ArrayList<>();

		String appUrl = httpServletRequest.getScheme()
				.concat("://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort());

		String requiredFilePath = baseDirPath + directory;

		try {

			File isDirectory = new File(requiredFilePath);

			if (!isDirectory.exists()) {
				isDirectory.mkdir();
			}

			for (MultipartFile multipartFile : multipartFiles) {

				File file = convertMultiPartToFile(multipartFile);

				if (Files.exists(Paths.get(requiredFilePath, file.getName()))) {
					return false; // If any file already exists, return false
				}

				PDDocument document = Loader.loadPDF(file);

				// Splitter Class
				Splitter splitting = new Splitter();

				// Splitting the pages into multiple PDFs
				List<PDDocument> pages = splitting.split(document);

				// Using an iterator to traverse all pages
				Iterator<PDDocument> iteration = pages.listIterator();

				// Saving each page as an individual document
				while (iteration.hasNext()) {

					String uniqueFileName = multipartFile.getOriginalFilename().substring(0,
							multipartFile.getOriginalFilename().length() - 4) + UUID.randomUUID().toString();

					DocumentInfoDto documentInfoDto = new DocumentInfoDto();

					documentInfoDto.setFileSize(String.valueOf(multipartFile.getSize()));
					documentInfoDto.setExtension(multipartFile.getOriginalFilename()
							.substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
					documentInfoDto
							.setStartIndex((multipartFile.getBytes().length + 1) - (multipartFile.getBytes().length));
					documentInfoDto.setEndIndex(document.getNumberOfPages());
					documentInfoDto.setDirectory(directory);
					documentInfoDto.setFileName(uniqueFileName);
					documentInfoDto.setIsActive(Boolean.FALSE);
					documentInfoDto.setFileUrl(appUrl.concat(baseUrlPath + uniqueFileName));

					PDDocument pd = iteration.next();

					pd.save(requiredFilePath + "\\" + uniqueFileName + ".pdf");

					listDocumentInfoDtos.add(documentInfoDto);
				}

				document.close();
			}

			List<DocumentModel> listOfDocumentModel = documentRepository
					.saveAll(documentMapper.mapDocumentInfoDtoListToDocumentModelList(listDocumentInfoDtos));

			return !listOfDocumentModel.isEmpty();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
			logger.error("Error occurs in Class DocumentServiceImpl in method --> uploadMultipleDocuments",
					e.getMessage());
		}

		return false;
	}

	@Override
	public List<DocumentInfoDto> getAllFileInfo() {
		// TODO Auto-generated method stub

		List<DocumentInfoDto> listDocumentInfoDtos = documentMapper
				.mapDocumentInfoModelListToDocumentDtoList(documentRepository.findAll());

		return listDocumentInfoDtos;
	}

	@Override
	public ResponseEntity<Resource> showFile(String fileName) {
		// TODO Auto-generated method stub

		DocumentModel documentModel = documentRepository.findByFileName(fileName).orElseThrow();

		File dir = new File(baseDirPath + documentModel.getDirectory() + "\\" + fileName + ".pdf");

		try {
			if (dir.exists()) {
				Resource resource = new UrlResource(dir.toURI());

				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
				headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
			}
		} catch (Exception e) {
			e.getStackTrace();
			logger.error("Error ouccers in Class DocumentServiceImpl in method --> showFile", e.getMessage());
		}
		return null;
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
