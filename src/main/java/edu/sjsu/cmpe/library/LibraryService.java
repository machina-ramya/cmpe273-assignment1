package edu.sjsu.cmpe.library;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import edu.sjsu.cmpe.library.api.resources.BookResource;
import edu.sjsu.cmpe.library.api.resources.AuthorResource;
import edu.sjsu.cmpe.library.api.resources.RootResource;
import edu.sjsu.cmpe.library.api.resources.ReviewResource;
import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;
import edu.sjsu.cmpe.library.dao.BookDao;
import edu.sjsu.cmpe.library.dao.AuthorDao;
import edu.sjsu.cmpe.library.dao.ReviewDao;

public class LibraryService extends Service<LibraryServiceConfiguration> {

    public static void main(String[] args) throws Exception {
	new LibraryService().run(args);
    }

    @Override
    public void initialize(Bootstrap<LibraryServiceConfiguration> bootstrap) {
	bootstrap.setName("library-service");
    }

    @Override
    public void run(LibraryServiceConfiguration configuration,
	    Environment environment) throws Exception {

    	/** Root API */
    	environment.addResource(RootResource.class);

        /** Set of DAO's */
        BookDao   bookDao   = new BookDao();
        AuthorDao authorDao = new AuthorDao();
        ReviewDao reviewDao = new ReviewDao();

    	/** Books APIs */
        environment.addResource(new BookResource(bookDao, authorDao));

        /** Authors APIs */
        environment.addResource(new AuthorResource(authorDao, bookDao));

        /** Reviews APIs */
        environment.addResource(new ReviewResource(bookDao, reviewDao));
    }
}
