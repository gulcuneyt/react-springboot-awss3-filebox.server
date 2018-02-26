package com.smilepower.filebox.repository;

import java.io.InputStream;
import java.util.List;

import com.smilepower.filebox.FileboxServiceException;
import com.smilepower.filebox.model.BoxFile;
import com.smilepower.filebox.model.User;

public interface FileBoxRepository {

	public List<User> findUsers();

	public User saveUser(User user) throws FileboxServiceException;

	public void deleteUser(String userName) throws FileboxServiceException;

	public List<BoxFile> findUserFiles(String username);

	public void deleteFile(String fileId);

	public void uploadFile(String username, String fileName, InputStream inputStream);

	public byte[] downloadFile(String fileId) throws FileboxServiceException;

}
