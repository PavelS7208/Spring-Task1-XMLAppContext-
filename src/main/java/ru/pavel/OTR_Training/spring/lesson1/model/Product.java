package ru.pavel.OTR_Training.spring.lesson1.model;

import java.io.Serializable;
import java.math.BigDecimal;

public record Product(Long id, String title, BigDecimal price) implements Serializable {

}
