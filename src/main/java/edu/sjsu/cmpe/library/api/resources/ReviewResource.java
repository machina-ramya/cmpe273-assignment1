package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.System;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import com.yammer.dropwizard.config.Environment;

import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.ViewReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewListDto;
import edu.sjsu.cmpe.library.dao.ReviewDao;
import edu.sjsu.cmpe.library.dao.BookDao;

import java.util.ArrayList;

@Path("/v1/books/{isbn}/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ReviewResource {

	private BookDao   iBookDao;
	private ReviewDao iReviewDao;

    public ReviewResource(BookDao aBookDao, ReviewDao aReviewDao) {
    	iBookDao   = aBookDao;
    	iReviewDao = aReviewDao;
    }

    @GET
    @Path("/{id}")
    @Timed(name = "view-review")
    public Response viewReview(@PathParam("isbn") LongParam aIsbn,
    	      				   @PathParam("id") LongParam aId) {
		
		// Fetch the book
    	Book b = iBookDao.getBook(aIsbn.get());

    	// If book is found, search for the review 
    	// Otherwise send 404 - Not found status code. 

    	if( b == null ) {
			return Response.status(404).build();
    	}

    	// Fetch the review using the id.
		Review r = b.getReview( aId.get() );

		if( r == null ) {
			return Response.status(404).build();
		}

		// Create a response
		ViewReviewDto dto = new ViewReviewDto(r);

		dto.addLink(new LinkDto("view-review", 
			                    "/books/" + aIsbn.get() + "/reviews/" + aId, 
			                    "GET"));

		return Response.ok(dto).build();
    }

    @POST
    @Path("/")
    @Timed(name = "create-review")
    public Response createReview(@Valid Review aReview, @PathParam("isbn") LongParam aIsbn) {
		
		// Create a response
		ReviewDto dto = new ReviewDto();

		// Create the Review entry - 
		// It will create entries in book table and the Review table. 
		dto.createReview( aReview, aIsbn.get(), iBookDao, iReviewDao );

		dto.addLink(new LinkDto("view-review", 
			                    "/books/" + aIsbn.get() + "/reviews/" + aReview.getReviewId(), 
			                    "GET"));

		return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("/")
    @Timed(name = "view-all-reviews")
    public Response viewAllReviews(@PathParam("isbn") LongParam aIsbn) {
		
		// Fetch the book
    	Book b = iBookDao.getBook(aIsbn.get());

    	// If book is found, search for the reviews 
    	// Otherwise send 404 - Not found status code. 

    	if( b == null ) {
			return Response.status(404).build();
    	}

    	// Fetch all the reviews
		ArrayList<Review> reviews = b.getReviews();

		if( reviews == null || reviews.size() <= 0 ) {
			return Response.status(404).build();
		}

		// Create a response
		ReviewListDto dto = new ReviewListDto(reviews);

		return Response.ok(dto).build();
    }
}

