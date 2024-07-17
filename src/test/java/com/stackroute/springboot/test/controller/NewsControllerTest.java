package com.stackroute.springboot.test.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.springboot.controller.NewsController;
import com.stackroute.springboot.exception.NewsAlreadyExistsException;
import com.stackroute.springboot.model.News;
import com.stackroute.springboot.services.NewsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
public class NewsControllerTest 
{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NewsServiceImpl newsservice;

	private News news;

	@Before
	public void setup() {

		news = new News();
		news.setNewsId(1);
		news.setNewsTitle("Sports");
		news.setNewsDesc("Tennis");
	}

	@After
	public void tearDown() {
		news = null;
	}

	@Test
	public void testSaveNewsSuccess() throws Exception 
	{
		//whenever the request is coming we are returning the news object
		Mockito.when(newsservice.saveNews(news)).thenReturn(news);

		//How we send the request?
		//In mockMVC you can create the request by using the perform method
		//Inside that you can perform 
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news").contentType(MediaType.APPLICATION_JSON)
				.content(asJSON(news))).andExpect(status().isCreated());
	}
	@Test
	public void testSaveEmployeeFailure() throws Exception {
		Mockito.when(newsservice.saveNews(Mockito.any())).thenThrow(NewsAlreadyExistsException.class);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news").contentType(MediaType.APPLICATION_JSON)
				.content(asJSON(news))).andExpect(status().isConflict());

	}
	public static String asJSON(Object obj) {
		try {
			String empObjString = new ObjectMapper().writeValueAsString(obj);
			return empObjString;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
