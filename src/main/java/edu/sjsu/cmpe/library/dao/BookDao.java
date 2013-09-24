package edu.sjsu.cmpe.library.dao;

import edu.sjsu.cmpe.library.domain.Book;
import java.util.HashMap;

public class BookDao {

    private HashMap iHashMap;

    /**
     * @param book
     */
    public BookDao() {

	   super();

	   iHashMap = new HashMap(100);
    }

    public long getNewISBN() {
        return iHashMap.size() + 1;
    }

    public int createBook(Book aBook) {

        iHashMap.put(aBook.getIsbn(), aBook);

        return 0;
    }

    public void deleteBook(long aIsbn) {
        iHashMap.remove(aIsbn);
    }

    public Book getBook(long aIsbn) {
        if( iHashMap.containsKey(aIsbn) ) {
            return (Book) iHashMap.get(aIsbn);
        }

        return null;
    }

    public void updateBook( Book aBook, 
                            String aTitle,
                            String aPublicationDate,
                            String aLanguage,
                            Integer aNumPages, 
                            String aStatus) {
        if( aBook == null || !iHashMap.containsValue(aBook) ) 
            return;

        if( aTitle != null ) {
            aBook.setTitle( aTitle );
        }
        if( aPublicationDate != null ) {
            aBook.setPublicationDate( aPublicationDate );
        }
        if( aLanguage != null ) {
            aBook.setLanguage( aLanguage );
        }
        if( aNumPages != null ) {
            aBook.setNumPages( aNumPages.intValue() );
        }
        if( aStatus != null ) {
            aBook.setStatus( aStatus );
        }

        //System.out.println( "Updated Title = " + ((Book)iHashMap.get(aBook.getIsbn())).getTitle() );
    }
}
