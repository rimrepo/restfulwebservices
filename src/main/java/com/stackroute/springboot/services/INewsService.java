package com.stackroute.springboot.services;

import java.util.List;

import com.stackroute.springboot.exception.NewsAlreadyExistsException;
import com.stackroute.springboot.exception.NewsNotFoundException;
import com.stackroute.springboot.model.News;

public interface INewsService {
	public News getNewsById(int newsId) throws NewsNotFoundException;

	public List<News> getAllNews();

	public News saveNews(News news) throws NewsAlreadyExistsException;

	public News updateNews(News news) throws NewsNotFoundException;

	public boolean deleteNews(int newsId) throws NewsNotFoundException;
}