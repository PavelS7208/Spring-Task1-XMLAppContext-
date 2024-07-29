package ru.pavel.OTR_Training.spring.lesson1.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pavel.OTR_Training.spring.lesson1.service.CartService;
import ru.pavel.OTR_Training.spring.lesson1.service.OrderService;
import ru.pavel.OTR_Training.spring.lesson1.service.ProductService;
import ru.pavel.OTR_Training.spring.lesson1.service.UserService;
import ru.pavel.OTR_Training.spring.lesson1.storage.Storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class OrderServiceTest {

    final Product testProduct1 = new Product(1L, "Футболка", BigDecimal.valueOf(500));
    final Product testProduct2 = new Product(2L, "Ручка", BigDecimal.valueOf(100));

    final User testUser1 = new User(1L, "Ivanov II");
    final User testUser2 = new User(2L, "Alex666");
    private final ProductService productService;
    private final CartService cartService;


    public OrderServiceTest() {
        Storage storage = new StorageTest();
        this.productService = new ProductService(storage);
        this.cartService = new CartService(new UserService(storage));
    }

    @Test
    @DisplayName("CreateEmptyOrder Test")
    void createEmptyOrder() {

        var orderService = new OrderService();
        var cart = cartService.getCartByUserId(1L);
        assertThat(cart.getProducts().isEmpty()).isTrue();

        Exception exception = assertThrows(RuntimeException.class, () -> orderService.createOrderByCart(cart));
        assertThat(exception.getMessage()).contains("Cart is empty");

    }

    @Test
    @DisplayName("CreateOrderWithProduct Test")
    void createOrder() {

        Cart cart = cartService.getCartByUserId(1L);

        productService.findByTitle(testProduct1.title())
                .ifPresent(product -> cart.add(product, 2));
        productService.findById(testProduct2.id())
                .ifPresent(product -> cart.add(product, 3));

        var exceptedCost = testProduct1.price()
                .multiply(BigDecimal.valueOf(2))
                .add(testProduct2.price().multiply(BigDecimal.valueOf(3)));

        // Должна вернуть туже саму корзину
        var cart2 = cartService.getCartByUserId(1L);
        assertEquals(cart2, cart);

        // Формируем заказ и обнуляем корзину
        var orderService = new OrderService();
        var order = orderService.createOrderByCart(cart);
        order.setAddress("Пункт Выдачи");

        // Проверка корректности расчета стоимости заказа
        assertEquals(order.getCost(), exceptedCost);
        // Проверка корректности строк в заказе
        assertEquals(order.orderDetailList.size(), 2);
        // После заказа корзина должна обнулиться
        assertThat(cartService.getCartByUserId(1L).getProducts().isEmpty()).isTrue();
        // Пользователь в заказе и корзине один и тот же
        assertEquals(order.getUser(), cart.getUser());
        assertEquals(cart.getUser(), testUser1);
    }

    class StorageTest extends Storage {

        @Override
        public List<Product> getAllProducts() {
            var products = new ArrayList<Product>();
            products.add(testProduct1);
            products.add(testProduct2);
            return products;
        }

        @Override
        public List<User> getAllUsers() {
            var users = new ArrayList<User>();
            users.add(testUser1);
            users.add(testUser2);
            return users;
        }
    }

}