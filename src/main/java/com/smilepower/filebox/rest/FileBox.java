package com.smilepower.filebox.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smilepower.filebox.FileboxServiceException;
import com.smilepower.filebox.model.BoxFile;
import com.smilepower.filebox.model.User;
import com.smilepower.filebox.service.FileBoxService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class FileBox {

	@Autowired
	private FileBoxService fileBoxService;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public List<User> getUsers() {
		return fileBoxService.getUsers();
	}

	@RequestMapping(path = "/", method = RequestMethod.PUT)
	public void createUser(@RequestBody User user) throws FileboxServiceException {
		user.setNameSurname(user.getNameSurname().replaceAll(" ", "") + "/");
		fileBoxService.createUser(user);
	}

	@RequestMapping(path = "/{username}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("username") String username) throws FileboxServiceException {
		fileBoxService.deleteUser(username);
	}

	@RequestMapping(path = "/{username}/files/", method = RequestMethod.GET)
	public List<BoxFile> getFiles(@PathVariable("username") String username) {
		return fileBoxService.getFiles(username);
	}

	@RequestMapping(path = "/{username}/upload/", method = RequestMethod.POST)
	public void uploadFile(@PathVariable("username") String username, @RequestParam("file") MultipartFile file)
			throws IOException {
		fileBoxService.uploadFile(username, file.getOriginalFilename(), file.getInputStream());
	}

	
	@RequestMapping(path = "/files/", method = RequestMethod.DELETE)
	public void deleteFile(@RequestParam("fileId") String fileId) {
		fileBoxService.deleteFile(fileId);
	}

	@RequestMapping(path = "/files/", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadFile(@RequestParam("fileId") String fileId) throws FileboxServiceException {
		byte[] bytes = fileBoxService.downloadFile(fileId);

		/*
		 * File file = new File("/Users/cuneytgul/Desktop/test2.pages"); try {
		 * file.createNewFile(); FileCopyUtils.copy(bytes, file); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 */
		
		Path path = Paths.get("/users/cuneytgul/downloads/dosya.txt");
        try {
			Files.write(path, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(new MediaType("application", "pdf"));
		//httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", "dosya2.pages");
		httpHeaders.add("filename", "dosya2.pages");

		//return new ResponseEntity<>("Test Message".getBytes(), httpHeaders, HttpStatus.OK);
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}
}
