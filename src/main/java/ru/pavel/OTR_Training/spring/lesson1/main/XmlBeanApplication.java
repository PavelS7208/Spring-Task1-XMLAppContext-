package ru.pavel.OTR_Training.spring.lesson1.main;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pavel.OTR_Training.spring.lesson1.model.Cart;
import ru.pavel.OTR_Training.spring.lesson1.model.Order;
import ru.pavel.OTR_Training.spring.lesson1.service.CartService;
import ru.pavel.OTR_Training.spring.lesson1.service.OrderService;
import ru.pavel.OTR_Training.spring.lesson1.service.ProductService;
import ru.pavel.OTR_Training.spring.lesson1.service.UserService;

public class XmlBeanApplication {

    public static void main(String[] args) {

        //  Создаем бины сервисы из конфига.
        //  Данные для работы берутся из предзаполненных коллекций User и Product
        // Или из БД H2 (можно поменять в конфигурацию)
        //try (var context = new ClassPathXmlApplicationContext("lesson1/appContextDataInCollection.xml")) {
        try (var context = new ClassPathXmlApplicationContext("lesson1/appContextDataInDB.xml")) {

            var productService = context.getBean(ProductService.class);
            var cartService = context.getBean(CartService.class);
            var userService = context.getBean(UserService.class);

            // Кидаем в корзину товары из Хранилища и создаем заказ
            // Для пользователя первого в списке
            var user1 = userService.stream().findFirst().orElseThrow();
            Order order1 = createOrder1(
                    cartService.getCartByUserId(user1.id()),
                    productService,
                    context.getBean(OrderService.class));
            order1.setAddress("Главный пункт выдачи в городе");
            order1.printDetail();

            //   и еще один
            var user2 = userService.stream().skip(2).findFirst().orElseThrow();
            Order order2 = createOrder2(
                    cartService.getCartByUserId(user2.id()),
                    productService,
                    context.getBean(OrderService.class));
            order2.setAddress("CDEC");
            order2.printDetail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static Order createOrder1(Cart cart,
                                      ProductService productService,
                                      OrderService orderService) {

        productService.stream().filter(p -> "Ручка".equals(p.title())).findAny()
                .ifPresent(product -> cart.add(product, 2));

        productService.stream().filter(p -> "Карандаш".equals(p.title())).findAny()
                .ifPresent(product -> cart.add(product, 2));

        productService.stream().filter(p -> "Пенал".equals(p.title())).findAny()
                .ifPresent(product -> cart.add(product, 1));

        return orderService.createOrderByCart(cart);
    }

    private static Order createOrder2(Cart cart,
                                      ProductService productService,
                                      OrderService orderService) {
        //  Берем товар первый в списке и последний товар по номеру id
        productService.stream()
                .findFirst()
                .ifPresent(product -> cart.add(product, 1));

        productService.findById(productService.getMaxId())
                .ifPresent(product -> cart.add(product, 1));

        return orderService.createOrderByCart(cart);
    }

}
