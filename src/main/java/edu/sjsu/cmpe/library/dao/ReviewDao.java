package edu.sjsu.cmpe.library.dao;

import edu.sjsu.cmpe.library.domain.Review;
import java.util.HashMap;
import java.util.Iterator;

public class ReviewDao {

    private HashMap iHashMap;

    /**
     * 
     */
    public ReviewDao() {

	   super();

       iHashMap = new HashMap(100);
    }

    public long getNewReviewId() {
        return iHashMap.size() + 1;
    }

    public void createReview(Review aReview) {

        iHashMap.put(aReview.getReviewId(), aReview);
    }

    public Review getReview(long aReviewId) {

        if( iHashMap.containsKey(aReviewId) ) {
            return (Review) iHashMap.get(aReviewId);
        }
        
        return null;
    }
}
