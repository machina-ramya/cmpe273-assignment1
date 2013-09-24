package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dao.ReviewDao;

@JsonPropertyOrder(alphabetic = true)
public class ViewReviewDto extends LinksDto {

    private Review iReview;

    public ViewReviewDto() {
        super();
    }
    
    public ViewReviewDto(Review aReview) {
	   super();

       iReview = aReview;
    }

    public void setReview(Review aReview) {
        iReview = aReview;
    }

    public Review getReview() {
        return iReview;
    }
}
