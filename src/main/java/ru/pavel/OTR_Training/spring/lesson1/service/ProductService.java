package ru.pavel.OTR_Training.spring.lesson1.service;

import ru.pavel.OTR_Training.spring.lesson1.model.Product;
import ru.pavel.OTR_Training.spring.lesson1.storage.Storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ProductService {


    private final List<Product> products;
    private final Storage storage;

    public ProductService(Storage productStorage) {
        this.storage = productStorage;
        this.products = storage.getAllProducts();
    }

    public Optional<Product> findById(Long id) {
        return products.stream().filter(product -> id.equals(product.id())).findAny();
    }

    public Optional<Product> findByTitle(String title) {
        return products.stream().filter(product -> title.equals(product.title())).findAny();
    }

    public Stream<Product> stream() {
        return products.stream();
    }

    public Long getMaxId() {
        return products.stream()
                .map(Product::id)
                .max(Long::compare)
                .orElseThrow();
    }

}
