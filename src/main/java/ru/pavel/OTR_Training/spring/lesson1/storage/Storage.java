package ru.pavel.OTR_Training.spring.lesson1.storage;

import ru.pavel.OTR_Training.spring.lesson1.model.Product;
import ru.pavel.OTR_Training.spring.lesson1.model.User;

import java.util.List;

public abstract class Storage {

    abstract public List<Product> getAllProducts();

    abstract public List<User> getAllUsers();

    private String storageType;

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}
