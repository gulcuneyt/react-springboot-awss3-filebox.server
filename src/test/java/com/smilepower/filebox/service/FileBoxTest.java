package com.smilepower.filebox.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.smilepower.filebox.model.BoxFile;
import com.smilepower.filebox.model.User;
import com.smilepower.filebox.repository.FileBoxRepository;

import net.minidev.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FileBoxTest extends BaseRestTest {

	@MockBean
	private FileBoxService fileBoxService;

	@MockBean
	FileBoxRepository fileBoxRepository;

	private List<User> users;
	private List<BoxFile> files;

	@Before
	public void setup() throws Exception {
		super.setup();

		users = new ArrayList<>();
		files = new ArrayList<>();

		User user = new User("johnsmith", "johnsmith");

		users.add(user);
		users.add(new User("jacksparrow", "jacksparrow"));
		users.add(new User("orlandobloom", "orlandobloom"));

		BoxFile file = new BoxFile("johnsmith/cv.pdf", "cd.pdf");
		file.setUser(user);
		files.add(file);

		BoxFile file1 = new BoxFile("johnsmith/plan.xls", "plan.xls");
		file1.setUser(user);
		files.add(file1);

	}

	@Test
	public void getUsers() throws Exception {

		when(fileBoxService.getUsers()).thenReturn(users);

		mockMvc.perform(get("/users/")).andExpect(status().isOk()).andExpect(jsonPath("$", isA(JSONArray.class)))
				.andExpect(jsonPath("$.length()", is(3))).andReturn();
	}

	@Test
	public void createUser() throws Exception {
		User user = users.get(0);

		when(fileBoxService.createUser(user)).thenReturn(user);

		String content = json(user);

		mockMvc.perform(put("/users/").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteUser() throws Exception {
		User user = users.get(0);
		doNothing().when(fileBoxService).deleteUser(user.getId());
		mockMvc.perform(delete("/users/{id}", user.getId())).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getFiles() throws Exception {
		User user = users.get(0);
		when(fileBoxService.getFiles(user.getId())).thenReturn(files);

		mockMvc.perform(get("/users/{username}/files/", user.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$", isA(JSONArray.class))).andExpect(jsonPath("$.length()", is(2))).andReturn();
	}

	@Test
	public void uploadFile() throws Exception {

		User user = users.get(0);
		byte[] text = "This is test".getBytes();

		doNothing().when(fileBoxService).uploadFile(user.getNameSurname(), "test.txt", new ByteArrayInputStream(text));

		MockMultipartFile fstmp = new MockMultipartFile("file", "test.txt", "text/plain", text);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/users/{username}/upload/", user.getId()).file(fstmp))
				.andExpect(status().isOk());
	}

	@Test
	public void downloadFile() throws Exception {

		byte[] text = "This is test".getBytes();

		when(fileBoxService.downloadFile("johnsmith/plan.xls")).thenReturn(text);

		mockMvc.perform(get("/users/files/").param("fileId", "johnsmith/plan.xls")).andExpect(status().isOk())
				.andExpect(content().bytes(text)).andReturn();
	}

}
