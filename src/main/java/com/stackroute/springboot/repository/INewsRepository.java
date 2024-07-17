package com.stackroute.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.springboot.model.News;

@Repository
public interface INewsRepository extends JpaRepository<News, Integer> 
{
	
}
