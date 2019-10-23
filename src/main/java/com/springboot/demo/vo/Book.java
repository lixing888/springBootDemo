package com.springboot.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author lixing
 */
@Data
@Document(indexName = "oooodin", type = "book")
public class Book {
    private Integer id;
    @JsonProperty(value = "book_name")
    private String bookName;
    private String author;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

}
