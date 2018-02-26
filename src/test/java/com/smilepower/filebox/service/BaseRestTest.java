package com.smilepower.filebox.service;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseRestTest {

	protected static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	protected MockMvc mockMvc;

	protected void setup() throws Exception {

		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	protected String json(Object o) throws IOException {

		return objectMapper.writeValueAsString(o);
	}

}
