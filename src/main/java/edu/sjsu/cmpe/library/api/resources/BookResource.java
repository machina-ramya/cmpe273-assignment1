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

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dao.BookDao;
import edu.sjsu.cmpe.library.dto.ViewBookDto;
import edu.sjsu.cmpe.library.dao.AuthorDao;
import edu.sjsu.cmpe.library.dto.LinkDto;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookResource {

	private BookDao   iBookDao;
	private AuthorDao iAuthorDao;

    public BookResource(BookDao aBookDao, AuthorDao aAuthorDao) {
    	iBookDao   = aBookDao;
    	iAuthorDao = aAuthorDao;
    }

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response viewBook(@PathParam("isbn") LongParam aIsbn) {
		
		// Fetch the book
    	Book b = iBookDao.getBook(aIsbn.get());

    	// If book is found create a response. 
    	// Otherwise send 404 - Not found status code. 

    	if( b != null ) {

    		// Create a response
			ViewBookDto dto = new ViewBookDto(b);

			dto.addLink(new LinkDto("view-book", 
				                 "/books/" + b.getIsbn(),"GET"));
			dto.addLink(new LinkDto("update-book",
				                 "/books/" + b.getIsbn(), "PUT"));
			dto.addLink(new LinkDto("delete-book",
				                 "/books/" + b.getIsbn(), "DELETE"));
			dto.addLink(new LinkDto("create-review",
				                 "/books/" + b.getIsbn() + "/reviews", "POST"));

			if( b.reviewCount() > 0 ) {
				dto.addLink(new LinkDto("view-all-reviews",
				                 "/books/" + b.getIsbn() + "/reviews", "GET"));
			}

			return Response.ok(dto).build();
		}

		return Response.status(404).build();
    }

    @POST
    @Path("/")
    @Timed(name = "create-book")
    public Response createBookEntry(@Valid Book aBook) {
		
		// Create a response
		BookDto dto = new BookDto();

		// Create the book entry - 
		// It will create entries in book table and the author table. 
		dto.createBook( aBook, iBookDao, iAuthorDao );

		dto.addLink(new LinkDto("view-book", 
			                 "/books/" + aBook.getIsbn(),"GET"));
		dto.addLink(new LinkDto("update-book",
			                 "/books/" + aBook.getIsbn(), "PUT"));
		dto.addLink(new LinkDto("delete-book",
			                 "/books/" + aBook.getIsbn(), "DELETE"));
		dto.addLink(new LinkDto("create-review",
			                 "/books/" + aBook.getIsbn() + "/reviews", "POST"));

		return Response.status(201).entity(dto).build();
    }

    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBookEntry(@PathParam("isbn") LongParam aIsbn) {
		
    	// Delete the book entry
    	iBookDao.deleteBook(aIsbn.get());

		// Create a response
		BookDto dto = new BookDto();

		dto.addLink(new LinkDto("create-book", "/books/","POST"));

		return Response.ok(dto).build();
    }

    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBookEntry(@PathParam("isbn") LongParam aIsbn, 
    	                            @QueryParam("title") String aTitle,
    	                            @QueryParam("publication-date") String aPublicationDate,
    	                            @QueryParam("language") String aLanguage,
    	                            @QueryParam("num-pages") Integer aNumPages,
    	                            @QueryParam("status") String aStatus) {
		
		// Update the book
    	Book b = iBookDao.getBook(aIsbn.get());
    	if( b != null ) {

    		// If the status value is not an allowed value, 
    		// then send an error and abort the update operation. 

    		if( aStatus != null && !b.isValidStatus(aStatus) ) {
    			return Response.status(400).build();
    		}

    		iBookDao.updateBook(b, aTitle, aPublicationDate, aLanguage, aNumPages, aStatus);

    		// Create a response
			BookDto dto = new BookDto();

			dto.addLink(new LinkDto("view-book", 
				                 "/books/" + b.getIsbn(),"GET"));
			dto.addLink(new LinkDto("update-book",
				                 "/books/" + b.getIsbn(), "PUT"));
			dto.addLink(new LinkDto("delete-book",
				                 "/books/" + b.getIsbn(), "DELETE"));
			dto.addLink(new LinkDto("create-review",
				                 "/books/" + b.getIsbn() + "/reviews", "POST"));

			if( b.reviewCount() > 0 ) {
				dto.addLink(new LinkDto("view-all-reviews",
				                 "/books/" + b.getIsbn() + "/reviews", "GET"));
			}

			return Response.ok(dto).build();
		}

		return Response.status(404).build();
    }
}

