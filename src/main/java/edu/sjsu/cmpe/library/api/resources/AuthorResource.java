package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.System;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import com.yammer.dropwizard.config.Environment;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorListDto;
import edu.sjsu.cmpe.library.dao.AuthorDao;
import edu.sjsu.cmpe.library.dao.BookDao;
import edu.sjsu.cmpe.library.dto.LinkDto;

@Path("v1/books/{isbn}/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthorResource {

	private AuthorDao iAuthorDao;
    private BookDao   iBookDao;

    public AuthorResource(AuthorDao aAuthorDao, BookDao aBookDao) {
    	iAuthorDao = aAuthorDao;
        iBookDao   = aBookDao;
    }

    @GET
    @Path("/{id}")
    @Timed(name = "view-author")
    public Response viewAuthor(@PathParam("id") LongParam aAuthorId, 
    	                       @PathParam("isbn") LongParam aIsbn) 
    {
        Author author = iAuthorDao.getAuthor(aAuthorId.get());

        if( author == null ) {
            return Response.status(404).build();
        }

        // Validate that this author is one of the authors of the book. 
        Book b = iBookDao.getBook( aIsbn.get() );

        if( b == null ) {
            return Response.status(404).build();
        }

        if( !b.containsAuthor( author ) ) {
            return Response.status(404).build();
        }

		AuthorDto dto = new AuthorDto(author);

		dto.addLink(new LinkDto("view-author", 
			                    "/books/" + aIsbn +
			                    "/authors/" + aAuthorId, 
                                "GET" ));

		return Response.ok(dto).build();
    }

    @GET
    @Path("/")
    @Timed(name = "view-all-authors")
    public Response viewAllAuthors(@PathParam("isbn") LongParam aIsbn) 
    {
        // Fetch the book 
        Book b = iBookDao.getBook( aIsbn.get() );

        // If book is found, search for the reviews 
        // Otherwise send 404 - Not found status code. 

        if( b == null ) {
            return Response.status(404).build();
        }

        // Fetch all the authors
        ArrayList<Author> authors = b.getAuthors();

        if( authors == null || authors.size() <= 0 ) {
            return Response.status(404).build();
        }

        // Create a response
        AuthorListDto dto = new AuthorListDto(authors);

        return Response.ok(dto).build();
    }
}

