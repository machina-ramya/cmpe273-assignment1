package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dao.ReviewDao;

import java.util.ArrayList;

@JsonPropertyOrder(alphabetic = true)
public class ReviewListDto extends LinksDto {

    private ArrayList<Review> reviews;

    public ReviewListDto() {
        super();
    }
    
    public ReviewListDto(ArrayList<Review> aReviews) {
	   super();

       reviews = aReviews;
    }

    public void setReviews(ArrayList<Review> aReviews) {
        reviews = aReviews;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
