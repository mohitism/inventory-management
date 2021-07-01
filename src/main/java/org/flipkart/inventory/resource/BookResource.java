package org.flipkart.inventory.resource;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.flipkart.inventory.core.Author;
import org.flipkart.inventory.core.Book;
import org.flipkart.inventory.dao.AuthorDAO;
import org.flipkart.inventory.dao.BookDAO;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class BookResource {

    BookDAO bookDAO;

    @Inject
    public BookResource(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @POST
    @UnitOfWork
    public Book add(@Valid Book book) {
        Book newBook = bookDAO.save(book);
        return newBook;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Book get(@PathParam("id") Integer id){
        return bookDAO.findById(id);
    }
}
