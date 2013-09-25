package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dao.BookDao;
import edu.sjsu.cmpe.library.dao.AuthorDao;
import java.util.ArrayList;
import java.util.Iterator;

@JsonPropertyOrder(alphabetic = true)
public class ViewBookDto extends LinksDto {

    private Book iBook;

    public ViewBookDto() {
        super();
    }
    
    public ViewBookDto(Book aBook) {
	   super();

       iBook = aBook;
    }

    public void setBook(Book aBook) {
        iBook = aBook;
    }

    public Book getBook() {
        return iBook;
    }
}
