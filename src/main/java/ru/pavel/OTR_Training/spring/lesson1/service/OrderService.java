package ru.pavel.OTR_Training.spring.lesson1.service;

import ru.pavel.OTR_Training.spring.lesson1.model.Cart;
import ru.pavel.OTR_Training.spring.lesson1.model.Order;

public class OrderService {

    public Order createOrderByCart(Cart cart) {
        if(cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty.Can't create order");
        }
        var order = new Order(cart);
        cart.clear();
        return order;
    }
}
