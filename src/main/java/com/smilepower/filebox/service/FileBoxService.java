package com.smilepower.filebox.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smilepower.filebox.FileboxServiceException;
import com.smilepower.filebox.model.BoxFile;
import com.smilepower.filebox.model.User;

@Service
public interface FileBoxService {
	
	public List<User> getUsers();
	
	public User createUser(User user) throws FileboxServiceException;
	
	public void deleteUser(String userName) throws FileboxServiceException;
	
	public List<BoxFile> getFiles(String username);
	
	public void deleteFile(String fileId);
	
	public void uploadFile(String username, String fileName, InputStream inputStream);
	
	public byte[] downloadFile(String fileId) throws FileboxServiceException;

}
