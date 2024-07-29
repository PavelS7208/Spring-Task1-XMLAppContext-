package ru.pavel.OTR_Training.spring.lesson1.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;


public class Order {

    final Map<Product, Integer> orderDetailList;
    private final BigDecimal cost;
    private final User user;
    private String address;

    public Order(Cart cart) {
        orderDetailList = cart.getProducts();
        cost = orderDetailList.entrySet().stream()
                .map(e -> e.getKey().price().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        user = cart.getUser();
        address = "";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void printDetail() {
        System.out.println("Заказ");
        System.out.printf("Логин: id=%d name=%s\n", user.id(), user.name());
        System.out.printf("Адрес доставки: %s\n", address.isBlank() ? "Адрес доставки не указан" : address);
        System.out.println("------------------------------");
        orderDetailList.forEach((k, v) ->
                System.out.printf(
                        "Товар: %s Цена: %s Кол-во: %d Сумма: %s\n",
                        k.title(),
                        k.price().setScale(2, RoundingMode.HALF_UP).toPlainString(),
                        v,
                        k.price().multiply(BigDecimal.valueOf(v)).setScale(2, RoundingMode.HALF_UP).toPlainString())
        );
        System.out.println("------------------------------");
        System.out.printf("Итого: %s\n", cost.setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    public BigDecimal getCost() {
        return cost;
    }
}
