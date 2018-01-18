package org.packt.swarm.petstore;

import org.packt.swarm.petstore.api.CartItemView;
import org.packt.swarm.petstore.api.CatalogItemView;
import org.packt.swarm.petstore.pricing.api.Price;
import org.packt.swarm.petstore.cart.api.CartItem;
import org.packt.swarm.petstore.catalog.api.CatalogItem;
import org.packt.swarm.petstore.proxy.CartProxy;
import org.packt.swarm.petstore.proxy.CatalogProxy;
import org.packt.swarm.petstore.proxy.OrderProxy;
import org.packt.swarm.petstore.proxy.PaymentProxy;
import org.packt.swarm.petstore.proxy.PricingProxy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GatewayService {

    @Inject
    private CatalogProxy catalogProxy;

    @Inject
    private PricingProxy pricingProxy;

    @Inject
    private PaymentProxy paymentProxy;

    @Inject
    private CartProxy cartProxy;

    @Inject
    private OrderProxy orderProxy;



    public List<CatalogItemView> getItems() {
        List<CatalogItemView> pets = new ArrayList<>();
        for(CatalogItem item: catalogProxy.getAllItems()) {
            Price price = pricingProxy.getPrice(item.getItemId());

            CatalogItemView pet = new CatalogItemView();
            pet.setItemId(item.getItemId());
            pet.setName(item.getName());
            pet.setPrice(price.getPrice());
            pet.setQuantity(item.getQuantity());
            pet.setDescription(item.getDescription());

            pets.add(pet);
        }
        return pets;
    }

    public void addToCart(String customerId, CartItem item, boolean additive){
        cartProxy.addToCart(customerId, item, additive);
    }

    public void deleteFromCart(String customerId, String itemId){
        cartProxy.deleteFromCart(customerId, itemId);
    }

    public List<CartItemView> getCart(String customerId){
        List<CartItemView> results = new ArrayList<>();
        for(CartItem cartItem: cartProxy.getCart(customerId)){
            CartItemView result = new CartItemView();

            result.setItemId(cartItem.getItemId());
            result.setQuantity(cartItem.getQuantity());

            CatalogItem catalogItem = catalogProxy.getItem(cartItem.getItemId());
            result.setName(catalogItem.getName());

            Price price = pricingProxy.getPrice(catalogItem.getName());
            result.setPrice(price.getPrice());

            results.add(result);
        }

        System.out.println("JESTEM TUTAJ");

        return results;

    }

    public String buy(int customerId){
//        Cart cart = cartProxy.getCart(customerId);
//
//        Order order = createOrderFromCart(customerId, cart);
//        int orderId  = orderProxy.createOrder(order);
//
//        Payment payment = new Payment();
//        payment.setMerchantId(Constants.MERCHANT_ID);
//        payment.setDescription(String.format("ORDER_ID: %s", orderId));
//        payment.setAmount(order.getPrice());
//
//        Response response =  paymentProxy.createPayment(payment);
//
//        if(response.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode()){
//            throw new RuntimeException("Payment service unreachable");
//        }
//
//        return (String) response.readEntity(String.class);
        return "pies";
    }

    /*private Order createOrderFromCart(int customerId, Cart cart){
        Order order = new Order();
        order.setCustomerId(customerId);
        for(Cart.CartItemView ci : cart.getItems()){
            order.getItems().add(new Order.CartItemView(order, ci.getItemId(),ci.getQuantity()));
        }
        order.setPrice(cart.calculatePrice());
        return order;
    }*/


}
