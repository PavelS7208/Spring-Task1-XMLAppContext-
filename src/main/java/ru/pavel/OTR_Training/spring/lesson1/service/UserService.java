package ru.pavel.OTR_Training.spring.lesson1.service;

import ru.pavel.OTR_Training.spring.lesson1.model.User;
import ru.pavel.OTR_Training.spring.lesson1.storage.Storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserService {

    List<User> users;

    public UserService(Storage storage) {
        this.users = storage.getAllUsers();
    }

    public Optional<User> findById(Long userId) {
        return users.stream().filter(user -> userId.equals(user.id())).findAny();
    }

    public Stream<User> stream() {
        return users.stream();
    }
}
