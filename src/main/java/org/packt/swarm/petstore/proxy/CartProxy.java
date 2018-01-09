package org.packt.swarm.petstore.proxy;

import org.packt.swarm.petstore.cart.api.CartItem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class CartProxy {

    private static final String SERVICE_NAME = "cart-service";
    private static final String NAMESPACE = "petstore";
    private static final String SWARM_PORT = "8080";

    private String targetPath;

    @PostConstruct
    public void init() {
        String hostname = SERVICE_NAME + "." + NAMESPACE + ".svc";
        targetPath = "http://" + hostname + ":" + SWARM_PORT;
    }

    public List<org.packt.swarm.petstore.cart.api.CartItem> getCart(String customerId){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetPath+"/cart/"+customerId);
        return Arrays.asList(target.request(MediaType.APPLICATION_JSON).get(org.packt.swarm.petstore.cart.api.CartItem[].class));
    }

    public void addToCart(String customerId, CartItem item, boolean additive){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetPath+"/cart/"+customerId+"?additive="+additive);
        Arrays.asList(target.request(MediaType.APPLICATION_JSON).post(Entity.json(item), Void.class));
    }

    public void deleteFromCart(String customerId, String itemId){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetPath+"/cart/"+customerId+"/"+itemId);
        target.request(MediaType.APPLICATION_JSON).delete();
    }

}
