package org.flipkart.inventory.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import org.flipkart.inventory.core.Author;
import org.flipkart.inventory.core.Book;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Singleton
public class BookDAO extends AbstractDAO<Book> {

    @Inject
    public BookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Book> getAll() {
        return list((Query<Book>) namedQuery("org.flipkart.inventory.core.Book.findAll"));
    }

    public Book findById(int id) {
        return get(id);
    }

    public void delete(Book person) {
        currentSession().delete(person);
    }

    public void update(Book person) {
        persist(person);
    }

    public Book save(Book person) {
        return persist(person);
    }
}
