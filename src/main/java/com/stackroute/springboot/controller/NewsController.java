package com.stackroute.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.springboot.exception.NewsAlreadyExistsException;
import com.stackroute.springboot.exception.NewsNotFoundException;
import com.stackroute.springboot.model.News;
import com.stackroute.springboot.services.NewsServiceImpl;


@RestController
@RequestMapping("api/v1")
public class NewsController 
{
	
	private NewsServiceImpl newsservice;
	
	private ResponseEntity<?> responseEntity;
	

    @Autowired
    public NewsController(NewsServiceImpl newsservice) {
        this.newsservice = newsservice;
    }
	
    @PostMapping("/news")
    public ResponseEntity<?> saveNewsToDB(@RequestBody News news) throws NewsAlreadyExistsException {
        System.out.println("inside SaveNewsToDB" + news.getNewsTitle());
        try {
        	
        	System.out.println("inside SaveNewsToDB try");
            News createdNews = newsservice.saveNews(news);
            System.out.println("inside SaveNewsToDB After save called");
            responseEntity = new ResponseEntity<>(createdNews, HttpStatus.CREATED);

        } catch (NewsAlreadyExistsException e) {
            throw new NewsAlreadyExistsException();
        }

        return responseEntity;
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNews() {
        try {
            responseEntity = new ResponseEntity<>(newsservice.getAllNews(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable int newsId) throws NewsNotFoundException {
        try {
            News newsobj = newsservice.getNewsById(newsId);
            responseEntity = new ResponseEntity<>(newsobj, HttpStatus.OK);
        } catch (NewsNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/news")
    public ResponseEntity<?> updateNews(@RequestBody News news) throws NewsNotFoundException {
        try {
            News updatedNews = newsservice.updateNews(news);
            responseEntity = new ResponseEntity<>(updatedNews, HttpStatus.OK);
        } catch (NewsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Some internal error occured...", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @DeleteMapping("news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable int newsId) throws NewsNotFoundException {
        try {
            boolean status = newsservice.deleteNews(newsId);
            if (status)
                responseEntity = new ResponseEntity<>("News with ID:" + newsId + " is deleted successfully",
                        HttpStatus.OK);
            else
                responseEntity = new ResponseEntity<>("Some Internal Error Occurred..",
                        HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NewsNotFoundException e) {
            throw e;
        }
        return responseEntity;
    }
}