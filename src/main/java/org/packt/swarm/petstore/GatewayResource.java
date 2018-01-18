package org.packt.swarm.petstore;

import org.packt.swarm.petstore.api.CartItemView;
import org.packt.swarm.petstore.api.CatalogItemView;
import org.packt.swarm.petstore.cart.api.CartItem;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/")
public class GatewayResource {

    @Inject
    private GatewayService gatewayService;

    @GET
    @Path("/catalog/item")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        try {
            List<CatalogItemView> result = gatewayService.getItems();
            return Response.ok(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/cart/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") String customerId) {
        try {
            System.out.println("IDZIE GET CART");
            List<CartItemView> cart = gatewayService.getCart(customerId);
            return Response.ok(cart).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/cart/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToCart(@PathParam("customerId") String customerId, CartItem item, @QueryParam("additive") boolean additive) {
        try {
            System.out.println("IDZIE ADD TO CART");
            gatewayService.addToCart(customerId, item, additive);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/cart/{customerId}/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFromCart(@PathParam("customerId") String customerId, @PathParam("itemId") String itemId) {
        try {
            gatewayService.deleteFromCart(customerId, itemId);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


    @POST
    @Path("payment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response payment(@QueryParam("customerId") int customerId, @Context  SecurityContext securityContext){
        try {
            String paymentUUID = gatewayService.buy(customerId);
            return Response.ok(paymentUUID).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
