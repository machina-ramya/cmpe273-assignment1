package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dao.BookDao;
import edu.sjsu.cmpe.library.dao.AuthorDao;
import java.util.ArrayList;
import java.util.Iterator;

@JsonPropertyOrder(alphabetic = true)
public class BookDto extends LinksDto {

    public BookDto() {
        super();
    }
    
    public void createBook(Book aBook, BookDao aBookDao, AuthorDao aAuthorDao) {
        if( aBookDao == null || aAuthorDao == null ) {
            return;
        }

        // Generate a unique ISNB for the book.
        aBook.setIsbn( aBookDao.getNewISBN() );

        // Now create entries in the author table by 
        // reading author information from the book entry
        ArrayList<Author> authors = aBook.authorList();
        if( authors != null && authors.size() > 0 ) {

            // For each author, search the author table to see if there an entry already. 
            // If there is, fetch the id and update the book entry. 
            // If not, create an author entry with a new id and update the book entry. 

            Iterator<Author> iterator = authors.iterator();
            int i = 0;

            while(iterator.hasNext()) {
                Author author = (Author) iterator.next();
                long id = aAuthorDao.getAuthorId(author.getName());
                if( id <= 0 ) {
                    // Author not found, create a new id and put it in the table
                    id = aAuthorDao.getNewAuthorId();
                    author.setAuthorId( id );

                    aAuthorDao.createAuthor( author );
                }

                // Finally update the book entry
                aBook.setAuthorId(i, id);
                i++;
            }
        }

        // Create entry in the book table 
        aBookDao.createBook( aBook );
    }
}
