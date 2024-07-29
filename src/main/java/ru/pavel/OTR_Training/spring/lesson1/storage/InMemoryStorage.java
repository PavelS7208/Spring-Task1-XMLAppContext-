package ru.pavel.OTR_Training.spring.lesson1.storage;

import ru.pavel.OTR_Training.spring.lesson1.model.Product;
import ru.pavel.OTR_Training.spring.lesson1.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


//  Для тестирования работы создаем списки напрямую
public class InMemoryStorage extends Storage {

    private final List<User> users = new ArrayList<User>();
    private final List<Product> products = new ArrayList<Product>();

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    private void init() {
        users.add(new User(1L,"Ivanov II"));
        users.add(new User(2L,"Alex666"));
        users.add(new User(3L,"Marvel73"));

        products.add(new Product(1L,"Футболка", BigDecimal.valueOf(500)));
        products.add(new Product(2L,"Ручка", BigDecimal.valueOf(100)));
        products.add(new Product(3L,"Шорты", BigDecimal.valueOf(800)));
        products.add(new Product(4L,"Карандаш", BigDecimal.valueOf(50)));
        products.add(new Product(5L,"Пенал", BigDecimal.valueOf(250)));

    }
}
