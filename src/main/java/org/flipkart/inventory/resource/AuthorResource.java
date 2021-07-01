package org.flipkart.inventory.resource;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.flipkart.inventory.core.Author;
import org.flipkart.inventory.dao.AuthorDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/person")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class AuthorResource {

    AuthorDAO authorDAO;

    @Inject
    public AuthorResource(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @POST
    @UnitOfWork
    public Author add(@Valid Author person) {
        Author newPerson = authorDAO.save(person);
        return newPerson;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Author get(@PathParam("id") Integer id){
        return authorDAO.findById(id);
    }

}
