package org.packt.swarm.petstore;

import org.packt.swarm.petstore.model.Cart;
import org.packt.swarm.petstore.model.Item;
import org.packt.swarm.petstore.model.Pet;
import org.packt.swarm.petstore.model.Price;
import org.packt.swarm.petstore.proxy.CartProxy;
import org.packt.swarm.petstore.proxy.CatalogProxy;
import org.packt.swarm.petstore.proxy.PaymentProxy;
import org.packt.swarm.petstore.proxy.PricingProxy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PetstoreService {

    @Inject
    private CatalogProxy catalogProxy;

    @Inject
    private PricingProxy pricingProxy;

    @Inject
    private PaymentProxy paymentProxy;

    @Inject
    private CartProxy cartProxy;



    public List<Pet> getAvailablePets() {
        List<Pet> pets = new ArrayList<>();
        for(Item item: catalogProxy.getAllItems()) {
            Price price = pricingProxy.getPrice(item.getName());

            Pet pet = new Pet();
            pet.setName(item.getName());
            pet.setPrice(price.getPrice());
            pet.setQuantity(item.getQuantity());

            pets.add(pet);
        }
        return pets;
    }

    public void pay(int customerId){
        Cart cart = cartProxy.getCart(5);
        String paymentUID =  paymentProxy.makePayment(3);
        paymentProxy.checkStatus(paymentUID);
    }
}
