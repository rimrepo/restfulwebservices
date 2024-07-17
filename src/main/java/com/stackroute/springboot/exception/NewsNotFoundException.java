package com.stackroute.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND,reason="News Not Found Exists")
public class NewsNotFoundException extends Exception {
	
}