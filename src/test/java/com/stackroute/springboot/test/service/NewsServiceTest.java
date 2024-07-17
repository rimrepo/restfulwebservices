package com.stackroute.springboot.test.service;

import java.util.Date;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.springboot.exception.NewsAlreadyExistsException;
import com.stackroute.springboot.model.News;
import com.stackroute.springboot.repository.INewsRepository;
import com.stackroute.springboot.services.NewsServiceImpl;

@RunWith(SpringRunner.class)
public class NewsServiceTest {

	@MockBean
	private INewsRepository newsRepository;

	@InjectMocks
	private NewsServiceImpl newsservice;

	private News news;
	private Optional<News> optional;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		news = new News();
		news.setNewsId(1);
		news.setNewsTitle("Sports");
		news.setNewsDesc("Tennis");

		optional = Optional.of(news);

	}

	@After
	public void tearDown() {
		news = null;
		optional = null;
	}

	@Test
	public void testSaveEmployeeSuccess() throws NewsAlreadyExistsException {
		Mockito.when(newsRepository.save(news)).thenReturn(news);
		News createdNews = newsservice.saveNews(news);
		Assert.assertEquals(news.getNewsTitle(), createdNews.getNewsTitle());
	}
}