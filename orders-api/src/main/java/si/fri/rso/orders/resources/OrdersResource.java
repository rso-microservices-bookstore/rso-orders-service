package si.fri.rso.orders.resources;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

import org.eclipse.microprofile.metrics.ConcurrentGauge;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import si.fri.rso.models.Order;
import si.fri.rso.orders.cdi.configuration.OrdersProperties;
import si.fri.rso.orders.cdi.configuration.RestProperties;

@Path("/orders")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private RestProperties restProperties;

    @Inject
    @Metric(name = "orders_meter")
    private Meter ordersMeter;

    @Inject
    @Metric(name = "orders_counter")
    private ConcurrentGauge ordersCounter;
    /**
     * <p>Queries the database and returns a specific order based on the given id.</p>
     *
     * @param id The id of the wanted book.
     * @return Response object containing the requested book, or empty with the NOT_FOUND status.
     */
    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Integer id) {
        Order o = em.find(Order.class, id);

        if (o == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(o).build();
    }

    /**
     * <p>Creates the order for the provided book.</p>
     *
     * @param o The book object for which the order will be placed.
     * @return Response object containing the created order.
     */
    @POST
    public Response placeOrder(Order o) {
        ordersMeter.mark();
        ordersCounter.inc();

        if (o == null || o.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // Tu bo id kosarice
        Response cartResponse = ClientBuilder.newClient()
                .target(restProperties.getCartsUrl()).path("carts").path(Integer.toString(o.getCartId())).request().get();
        System.out.println(restProperties.getCartsUrl());
        if (!cartResponse.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // o.setBook(bookResponse.readEntity(Book.class));
        o.setOrderDate(new Date());
        em.getTransaction().begin();
        em.persist(o);
        em.flush();
        em.getTransaction().commit();
        return Response.status(Response.Status.CREATED).entity(o).build();
    }


    @DELETE
    @Path("/{id}")
    @Metered(name = "orders_deleting_meter")
    public Response finishOrder(@PathParam("id") Integer id) {
        Order o = em.find(Order.class, id);
        ordersCounter.dec();

        if (o == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        em.getTransaction().begin();
        em.remove(o);
        em.flush();
        em.getTransaction().commit();

        return Response.ok(o).build();
    }


}
