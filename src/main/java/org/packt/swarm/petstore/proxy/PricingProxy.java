package org.packt.swarm.petstore.proxy;

import org.packt.swarm.petstore.model.Price;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class PricingProxy {

    private static final String SERVICE_NAME = "pricing-service";
    private static final String NAMESPACE = "petstore";
    private static final String SWARM_PORT = "8080";

    private String targetPath;

    @PostConstruct
    public void init() {
        String hostname = SERVICE_NAME + "." + NAMESPACE + ".svc";
        targetPath = "http://" + hostname + ":" + SWARM_PORT;
    }

    public Price getPrice(String name, String token){
        System.out.println("UWAGA IDZIE GET PRICE");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetPath + "/price/" + name);
        return target.request(MediaType.APPLICATION_JSON)
                //.header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .get(Price.class);
    }
}
