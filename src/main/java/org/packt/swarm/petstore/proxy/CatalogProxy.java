package org.packt.swarm.petstore.proxy;

import org.packt.swarm.petstore.catalog.api.CatalogItem;
import org.packt.swarm.petstore.security.AuthTokenPropagationFilter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class CatalogProxy {

    private static final String SERVICE_NAME = "catalog-service";
    private static final String NAMESPACE = "petstore";
    private static final String SWARM_PORT = "8080";

    private String targetPath;

    @PostConstruct
    public void init() {
        String hostname = SERVICE_NAME + "." + NAMESPACE + ".svc";
        targetPath = "http://" + hostname + ":" + SWARM_PORT;
    }

    public List<CatalogItem> getAllItems(){
        Client client = ClientBuilder.newClient();
        client.register(new AuthTokenPropagationFilter());
        WebTarget target = client.target(targetPath+"/item");
        return Arrays.asList(target.request(MediaType.APPLICATION_JSON).get(CatalogItem[].class));
    }

    public CatalogItem getItem(String itemId){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(targetPath+"/item/"+itemId);
        return target.request(MediaType.APPLICATION_JSON).get(CatalogItem.class);
    }

}