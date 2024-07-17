package com.stackroute.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.springboot.exception.NewsAlreadyExistsException;
import com.stackroute.springboot.exception.NewsNotFoundException;
import com.stackroute.springboot.model.News;
import com.stackroute.springboot.repository.INewsRepository;

@Service
public class NewsServiceImpl implements INewsService {
	private INewsRepository newsRepository;

	@Autowired
	public NewsServiceImpl(INewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	@Override
	public News getNewsById(int newsId) throws NewsNotFoundException {
		Optional<News> optional = this.newsRepository.findById(newsId);

		News newsobj = null;

		if (optional.isPresent()) {
			newsobj = optional.get();
		} else {
			throw new NewsNotFoundException();
		}

		return newsobj;
	}

	@Override
	public List<News> getAllNews() {
		return this.newsRepository.findAll();
	}

	@Override
	public News saveNews(News news) throws NewsAlreadyExistsException {

		System.out.println("inside SaveNews ...");
		
		Optional<News> optional = this.newsRepository.findById(news.getNewsId());

		if (optional.isPresent()) {
			throw new NewsAlreadyExistsException();
		}

		News newsobj = this.newsRepository.save(news);

		System.out.println("inside SaveNews "+ newsobj.getNewsTitle());
		
		return newsobj;
	}

	@Override
	public News updateNews(News news) throws NewsNotFoundException {
		Optional<News> optional = this.newsRepository.findById(news.getNewsId());
		News newsobj = null;
		if (optional.isPresent()) {
			newsobj = this.newsRepository.save(news);
		} else {
			throw new NewsNotFoundException();
		}
		return newsobj;
	}

	@Override
	public boolean deleteNews(int newsId) throws NewsNotFoundException {
		Optional<News> optional = this.newsRepository.findById(newsId);
		boolean status = false;
		if (optional.isPresent()) {
			this.newsRepository.delete(optional.get());
			status = true;
		} else {
			throw new NewsNotFoundException();
		}
		return status;
	}
}