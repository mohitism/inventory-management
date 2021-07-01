package org.flipkart.inventory.dao;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import org.flipkart.inventory.core.Author;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Singleton
public class AuthorDAO extends AbstractDAO<Author> {

    @Inject
    public AuthorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Author> getAll() {
        return list((Query<Author>) namedQuery("org.flipkart.inventory.core.Author.findAll"));
    }

    public Author findById(int id) {
        return get(id);
    }

    public void delete(Author person) {
        currentSession().delete(person);
    }

    public void update(Author person) {
        persist(person);
    }

    public Author save(Author person) {
        return persist(person);
    }
}
