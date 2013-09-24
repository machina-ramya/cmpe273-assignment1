package edu.sjsu.cmpe.library.dao;

import edu.sjsu.cmpe.library.domain.Author;
import java.util.HashMap;
import java.util.Iterator;

public class AuthorDao {

    private HashMap iHashMap;

    /**
     * @param book
     */
    public AuthorDao() {

	   super();

       iHashMap = new HashMap(100);
    }

    public long getNewAuthorId() {
        return iHashMap.size() + 1;
    }

    public int createAuthor(Author aAuthor) {

        iHashMap.put(aAuthor.getAuthorId(), aAuthor);

        return 0;
    }

    public Author getAuthor(long aAuthorId) {

        if( iHashMap.containsKey(aAuthorId) ) {
            return (Author) iHashMap.get(aAuthorId);
        }
        
        return null;
    }

    public long getAuthorId(String aName) {

        Iterator<Author> iterator = iHashMap.values().iterator(); 
       
        while(iterator.hasNext()) {        
            Author a = iterator.next();
            if( a != null && a.getName() == aName ) {
                return a.getAuthorId();
            }
        }

        return -1;
    }

    private void printAuthors() {

        Iterator<Author> iterator = iHashMap.values().iterator(); 
        System.out.println("******** Authors ********");
        while(iterator.hasNext()) {        
            Author a = iterator.next();
            if( a != null ) {
                System.out.println(a.getName());
            }
        }
    }
}
