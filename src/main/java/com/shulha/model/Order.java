package com.shulha.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "our_order")
public class Order {
    @Getter(AccessLevel.NONE)
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<Car> cars = new ArrayList<>();
    @Id
//    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id")
    private String id;
    private LocalDateTime date;

    public Order() {
        setDateNow();
        id = UUID.randomUUID().toString();
    }

    private void setDateNow() {
        date = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("ORDER №%s%nDATE AND TIME: %s%nCARS: %s%n", id, date.format(DATE_TIME_FORMATTER), cars);
    }
}
