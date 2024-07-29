package ru.pavel.OTR_Training.spring.lesson1.service;

import ru.pavel.OTR_Training.spring.lesson1.model.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CartService {

    private final UserService userService;
    private final Map<Long, Cart> carts = new HashMap<>();

    public CartService(UserService userService) {
        this.userService = userService;
    }

    // Возвращаем корзину для пользователя или новую делаем если ее у него нет еще
    //  Бросаем NoSuchElementException если нет такого пользователя
    public Cart getCartByUserId(Long userId) {

        var user = userService.findById(userId).orElseThrow(() -> new NoSuchElementException("UserId not found"));

        carts.putIfAbsent(userId, new Cart(user));
        return carts.get(userId);

    }
}

