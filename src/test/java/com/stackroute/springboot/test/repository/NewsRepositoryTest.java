package com.stackroute.springboot.test.repository;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.springboot.model.News;
import com.stackroute.springboot.repository.INewsRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class NewsRepositoryTest 
{
	@Autowired
	private INewsRepository newsRepository;

	private News news;

	@Before
	public void setup() 
	{
		news = new News();
		news.setNewsId(1);
		news.setNewsTitle("Sports");
		news.setNewsDesc("Tennis");
	}
	
	@After
	public void tearDown() {
		news = null;
		newsRepository.deleteAll();
	}
	
	@Test
	public void testSaveNewsSuccess() {
		newsRepository.save(news);		
	   News createdNews = newsRepository.findById(1).get();
	   Assert.assertEquals(news.getNewsTitle(), createdNews.getNewsTitle());
	   
	   newsRepository.delete(news);
	}
}