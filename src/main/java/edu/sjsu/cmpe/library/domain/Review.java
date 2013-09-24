package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Review {

    private long iReviewId;
    
    @NotNull
    @Max(5)
    @Min(1)
    private Integer rating;

    @NotEmpty
    private String comment;

    public Review() {
        
    }

    // Setters and getters ... 

    /**
     * @return the id
     */
    public long getReviewId() {
       return iReviewId;
    }

    /**
     * @param id - the id to set
     */
    public void setReviewId(long aReviewId) {
       iReviewId = aReviewId;
    }

    public void setRating(int aRating) {
        rating = aRating;
    }

    public int getRating() {
        return rating.intValue();
    }

    public void setComment(String aComment) {
        comment = aComment;
    }

    public String getComment() {
        return comment;
    }
}
