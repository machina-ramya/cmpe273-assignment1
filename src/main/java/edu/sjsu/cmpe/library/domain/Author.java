package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Author {

    private long iAuthorId;
    private ArrayList iBookList;

    @JsonProperty("name")
    @NotEmpty
    private String iName;

    

    public Author() {
        iBookList = new ArrayList();
    }

    // Setters and getters ... 

    /**
     * @return the name
     */
    public String getName() {
       return iName;
    }

    /**
     * @param name - the name to set
     */
    public void setName(String aName) {
       iName = aName;
    }

    /**
     * @return the id
     */
    public long getAuthorId() {
       return iAuthorId;
    }

    /**
     * @param id - the id to set
     */
    public void setAuthorId(long aAuthorId) {
       iAuthorId = aAuthorId;
    }

    public void addBook(long aBookIsbn) {
        if( iBookList != null ) {
            iBookList.add( aBookIsbn );
        }
    }

    public ArrayList writtenBooks() {
        return iBookList;
    }

}
