package ru.pavel.OTR_Training.spring.lesson1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pavel.OTR_Training.spring.lesson1.service.CartService;
import ru.pavel.OTR_Training.spring.lesson1.service.OrderService;
import ru.pavel.OTR_Training.spring.lesson1.service.ProductService;
import ru.pavel.OTR_Training.spring.lesson1.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class XmlBeanApplicationTest {

    @ParameterizedTest(name = "Проверка для конфига ={0}")
    @CsvSource({"appContextDataInCollection.xml", "appContextDataInDB.xml"})
    @DisplayName("ProductServiceBeanCreate Test")
    void testProductServiceCreate(String config) {
        try (var context = new ClassPathXmlApplicationContext("lesson1/%s".formatted(config))) {

            var productService = context.getBean(ProductService.class);
            assertThat(productService).isNotNull();

            var userService = context.getBean(UserService.class);
            assertThat(userService).isNotNull();

            var cartService = context.getBean(CartService.class);
            assertThat(cartService).isNotNull();

            // Проверка prototype
            var orderService1 = context.getBean(OrderService.class);
            var orderService2 = context.getBean(OrderService.class);
            assertNotEquals(orderService1, orderService2);
        }
    }

}