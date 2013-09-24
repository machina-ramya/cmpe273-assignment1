package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dao.AuthorDao;

import java.util.ArrayList;

@JsonPropertyOrder(alphabetic = true)
public class AuthorListDto extends LinksDto {

    private ArrayList<Author> authors;

    public AuthorListDto() {
        super();
    }
    
    public AuthorListDto(ArrayList<Author> aAuthors) {
	   super();

       authors = aAuthors;
    }

    public void setAuthors(ArrayList<Author> aAuthors) {
        authors = aAuthors;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }
}
