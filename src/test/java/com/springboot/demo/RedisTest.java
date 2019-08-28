package com.springboot.demo;

import com.springboot.demo.service.BookRepository;
import com.springboot.demo.vo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void test01() {
        Book book = new Book();
        book.setId(1);
        book.setBookName("晋升");
        book.setAuthor("ice");
        bookRepository.index(book);
    }

}
