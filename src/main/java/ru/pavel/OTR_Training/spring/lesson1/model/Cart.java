package ru.pavel.OTR_Training.spring.lesson1.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    final private Map<Product,Integer> products;

    private final User user;

    public Cart(User user) {
        this.products = new HashMap<>();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void add(Product product, int quantity ) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        products.put(product,quantity);
    }

    public Map<Product,Integer> getProducts() {
        return new HashMap<>(products);
    }

    public void clear() {
        products.clear();
    }

}
