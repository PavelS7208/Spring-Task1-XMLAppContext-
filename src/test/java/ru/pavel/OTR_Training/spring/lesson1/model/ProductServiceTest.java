package ru.pavel.OTR_Training.spring.lesson1.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.pavel.OTR_Training.spring.lesson1.service.ProductService;
import ru.pavel.OTR_Training.spring.lesson1.storage.Storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceTest {

    ProductService service;

    public ProductServiceTest() {
        this.service = new ProductService(new Storage() {
            @Override
            public List<Product> getAllProducts() {

                var products = new ArrayList<Product>();
                products.add(new Product(1L, "Футболка", BigDecimal.valueOf(500)));
                products.add(new Product(2L, "Ручка", BigDecimal.valueOf(100)));
                return products;
            }

            @Override
            public List<User> getAllUsers() {
                return List.of();
            }
        });
    }


    @ParameterizedTest(name = "Для продукта id={0} title={1}")
    @CsvSource({"1,Футболка", "2,Ручка"})
    @DisplayName("FindById Test")
    void findById(Long id, String expected) {

        var product = service.findById(id);
        assertThat(product.get().title()).isEqualTo(expected);

    }

    @ParameterizedTest(name = "Для продукта title={0} price={1}")
    @CsvSource({"Футболка,500", "Ручка,100"})
    @DisplayName("FindByTitle Test")
    void findByTitle(String title, BigDecimal expected) {

        var product = service.findByTitle(title);
        assertThat(product.get().price()).isEqualTo(expected);
    }

    @Test
    @DisplayName("FindByIdException Test")
    void findByIdException() {

        var product = service.findById(555L);
        assertThrows(NoSuchElementException.class, product::get);
    }

    @Test
    @DisplayName("FindByTitleException Test")
    void findByTitleException() {

        var product = service.findByTitle("ААААА");
        assertThrows(NoSuchElementException.class, product::get);
    }


    @Test
    @DisplayName("Stream Test")
    void stream() {
        assertThat(service.stream().count()).isEqualTo(2L);
    }

    @Test
    @DisplayName("GetMaxId Test")
    void maxIdTest() {
        assertThat(service.getMaxId()).isEqualTo(2L);
    }
}