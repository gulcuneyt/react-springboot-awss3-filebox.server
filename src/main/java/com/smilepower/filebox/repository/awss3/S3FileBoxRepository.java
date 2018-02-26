package com.smilepower.filebox.repository.awss3;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.smilepower.filebox.AppProperties;
import com.smilepower.filebox.FileboxServiceException;
import com.smilepower.filebox.model.BoxFile;
import com.smilepower.filebox.model.User;
import com.smilepower.filebox.repository.FileBoxRepository;

@Service
public class S3FileBoxRepository implements FileBoxRepository {

	@Autowired
	private AppProperties appProperties;
	private AmazonS3 s3;

	@PostConstruct
	public void setup() {
		s3 = AmazonS3Client.builder().withRegion(Regions.EU_CENTRAL_1).build();
	}

	public List<User> findUsers() {
		ObjectListing ol = s3.listObjects(appProperties.getBucketName());
		List<S3ObjectSummary> objects = ol.getObjectSummaries();
		return objects.stream().filter(obj -> obj.getKey().endsWith("/"))
				.map(s3obj -> new User(s3obj.getKey().replace("/", ""), s3obj.getKey().replace("/", "")))
				.collect(Collectors.toList());
	}

	public User saveUser(User user) throws FileboxServiceException {

		try {
			s3.putObject(appProperties.getBucketName(), user.getNameSurname(), "");
		} catch (AmazonServiceException e) {
			throw new FileboxServiceException(e.getMessage());
		}
		return user;

	}

	public void deleteUser(String userName) throws FileboxServiceException {
		try {
			s3.deleteObject(appProperties.getBucketName(), userName + "/");
		} catch (AmazonServiceException e) {
			throw new FileboxServiceException(e.getMessage());
		}
	}

	@Override
	public List<BoxFile> findUserFiles(String username) {
		ObjectListing ol = s3.listObjects(appProperties.getBucketName(), username);
		List<S3ObjectSummary> objects = ol.getObjectSummaries();
		return objects.stream().filter(obj -> obj.getKey().contains(".")).map(
				s3obj -> new BoxFile(s3obj.getKey(), s3obj.getKey().substring(s3obj.getKey().lastIndexOf("/") + 1)))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteFile(String fileId) {
		s3.deleteObject(appProperties.getBucketName(), fileId);
	}

	@Override
	public void uploadFile(String username, String fileName, InputStream inputStream) {
		s3.putObject(appProperties.getBucketName(), username + "/" + fileName, inputStream, null);
	}

	@Override
	public byte[] downloadFile(String fileId) throws FileboxServiceException {
		S3Object s3Object = s3.getObject(appProperties.getBucketName(), fileId);
		try (S3ObjectInputStream objectInputStream = s3Object.getObjectContent()) {
			return IOUtils.toByteArray(objectInputStream);
		} catch (IOException e) {
			throw new FileboxServiceException(e.getMessage());
		}
	}

}
