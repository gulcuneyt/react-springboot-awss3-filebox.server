package com.smilepower.filebox.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smilepower.filebox.FileboxServiceException;
import com.smilepower.filebox.model.BoxFile;
import com.smilepower.filebox.model.User;
import com.smilepower.filebox.repository.FileBoxRepository;

@Service
public class FileBoxServiceImpl implements FileBoxService {

	@Autowired
	FileBoxRepository fileBoxRepository;

	@Override
	public List<User> getUsers() {
		return fileBoxRepository.findUsers();
	}

	@Override
	public User createUser(User user) throws FileboxServiceException {
		return fileBoxRepository.saveUser(user);
	}

	@Override
	public void deleteUser(String userName) throws FileboxServiceException {
		fileBoxRepository.deleteUser(userName);
	}

	@Override
	public List<BoxFile> getFiles(String username) {
		return fileBoxRepository.findUserFiles(username);
	}

	@Override
	public void deleteFile(String fileId) {
		fileBoxRepository.deleteFile(fileId);

	}

	@Override
	public void uploadFile(String username, String fileName, InputStream inputStream) {
		fileBoxRepository.uploadFile(username, fileName, inputStream);

	}

	@Override
	public byte[] downloadFile(String fileId) throws FileboxServiceException {
		return fileBoxRepository.downloadFile(fileId);

	}

}
