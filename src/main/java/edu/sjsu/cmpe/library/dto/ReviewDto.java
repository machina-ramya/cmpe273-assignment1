package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dao.ReviewDao;
import edu.sjsu.cmpe.library.dao.BookDao;

@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto {

    private ReviewDao iReviewDao;

    public ReviewDto() {
        super();
    }

    public void createReview(Review aReview, long aIsbn, 
                             BookDao aBookDao, ReviewDao aReviewDao) 
    {
        if( aBookDao == null || aReviewDao == null ) {
            return;
        }

        // Generate a unique id for the review.
        aReview.setReviewId( aReviewDao.getNewReviewId() );

        // Now create entries in the Review table and 
        // update the book entry in the book table
        aReviewDao.createReview( aReview );

        Book book = aBookDao.getBook(aIsbn);

        if( book != null ) {
            book.addReview( aReview );
        }
    }
}
