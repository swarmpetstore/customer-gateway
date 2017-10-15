package org.packt.swarm.petstore;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class CatalogProxy {

    private String catalogServiceHost;
    private String catalogServicePort;

    public CatalogProxy() {
        catalogServiceHost = System.getenv("CATALOG_SERVICE_SERVICE_HOST");
        catalogServicePort = System.getenv("CATALOG_SERVICE_SERVICE_PORT");
    }

    public List<Item> getAllItems(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://" + catalogServiceHost +":" + catalogServicePort + "/item/");
        return Arrays.asList(target.request(MediaType.APPLICATION_JSON).get(Item[].class));
    }

}