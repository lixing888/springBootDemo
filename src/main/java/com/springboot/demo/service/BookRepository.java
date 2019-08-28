package com.springboot.demo.service;

import com.springboot.demo.vo.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author lixing
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

}
