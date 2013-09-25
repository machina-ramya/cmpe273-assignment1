package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Iterator;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.LinkDto;

public class Book {

    @JsonProperty
    private long isbn;
    
    @NotEmpty
    @NotNull
    @JsonProperty
    private String title;

    @NotEmpty
    @NotNull
    @JsonProperty("publication-date")
    private String publicationDate;

    @JsonProperty
    private String language;

    @JsonProperty("num-pages")
    private int numPages;

    @JsonProperty
    private String status;

    private String[] availableStatusValues;

    private ArrayList<Author> iAuthorList;

    @JsonProperty
    private ArrayList<Review> reviews;

    public Book() {
        availableStatusValues = new String[4];
        availableStatusValues[0] = "available";
        availableStatusValues[1] = "checked-out";
        availableStatusValues[2] = "in-queue";
        availableStatusValues[3] = "lost";

        status = availableStatusValues[0];
    }

    // Setters and getters ... 

    /**
     * @return the isbn
     */
    public long getIsbn() {
	   return isbn;
    }

    /**
     * @param isbn - the isbn to set
     */
    public void setIsbn(long aISBN) {
	   this.isbn = aISBN;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	   return title;
    }

    /**
     * @param title - the title to set
     */
    public void setTitle(String aTitle) {
	   this.title = aTitle;
    }

    /**
     * @return the publicationDate
     */
    public String getPublicationDate() {
       return publicationDate;
    }

    /**
     * @param publicationDate - the publicationDate to set
     */
    public void setPublicationDate(@JsonProperty("publication-date") String aPublicationDate) {
       this.publicationDate = aPublicationDate;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
       return language;
    }

    /**
     * @param language - the language to set
     */
    public void setLanguage(String aLanguage) {
       this.language = aLanguage;
    }

    /**
     * @return the numPages
     */
    public int getNumPages() {
       return numPages;
    }

    /**
     * @param numPages - the numPages to set
     */
    public void setNumPages(@JsonProperty("num-pages") int aNumPages) {
       this.numPages = aNumPages;
    }

    public void setStatus(String aStatus) {
        status = aStatus; 
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Author> authorList() {
        return iAuthorList;
    }

    public ArrayList<LinkDto> getAuthors() {
        // If there are authors, then return the links to REST apis for authors. 
        if( iAuthorList == null || iAuthorList.size() <= 0 ) {
            return new ArrayList<LinkDto>();
        }

        ArrayList<LinkDto> links = new ArrayList<LinkDto>();

        for(int i = 0; i < iAuthorList.size(); i++) {

            Author a = iAuthorList.get(i);
            if( a != null ) {
                long id = a.getAuthorId();
                links.add(new LinkDto("view-author", 
                                      "/books/" + getIsbn() +
                                      "/authors/" + id, 
                                      "GET"));
            }
        }

        return links;
    }

    public void setAuthors(@JsonProperty("authors") ArrayList<Author> aAuthors) {
        iAuthorList = aAuthors;
    }

    public void setAuthorId(int aPos, long aAuthorId) {
        if( iAuthorList != null && iAuthorList.size() > 0 && 
            aPos >= 0 && aPos < iAuthorList.size() ) {
            iAuthorList.get(aPos).setAuthorId( aAuthorId );
        }
    }

    public boolean containsAuthor(Author aAuthor) {
        if( iAuthorList == null || iAuthorList.size() <= 0 || aAuthor == null) {
            return false;
        }

        Iterator<Author> i = iAuthorList.iterator();
        long aId = aAuthor.getAuthorId();

        while( i.hasNext() ) {
            Author a = (Author) i.next();
            if( a != null && a.getAuthorId() == aId ) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review aReview) {
        if( aReview == null ) {
            return;
        }

        if( reviews == null ) {
            reviews = new ArrayList<Review>();
        }

        reviews.add( aReview );
    }

    public int reviewCount() {
        if( reviews == null ) {
            return 0;
        }
        
        return reviews.size();
    }

    public Review getReview(long aReviewId) {
        if( reviews == null ) {
            return null;
        }

        Iterator<Review> iter = reviews.iterator();
        while( iter.hasNext() ) {
            Review r = iter.next();
            if( r != null && r.getReviewId() == aReviewId ) {
                return r;
            }
        }

        return null;
    }

    public boolean isValidStatus(String aStatus) {
        if( aStatus == null || aStatus.length() == 0 ) {
            return false;
        }

        for(int i = 0; i < 4; i++) {
            if( aStatus.compareToIgnoreCase(availableStatusValues[i]) == 0 ) 
                return true;
        }

        return false;
    }
}
