package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dao.AuthorDao;

@JsonPropertyOrder(alphabetic = true)
public class AuthorDto extends LinksDto {

    private Author    iAuthor;
    private AuthorDao iAuthorDao;

    /**
     * @param Author
     */
    public AuthorDto(Author aAuthor) {

        super();

        iAuthor = aAuthor;
    }

    /**
     * @return the Author
     */
    public Author getAuthor() {
        return iAuthor;
    }

    /**
     * @param author - the author to set
     */
    public void setAuthor(Author aAuthor) {
	     iAuthor = aAuthor;
    }
}
