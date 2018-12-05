package com.whereIsMyMoney.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases = new ArrayList<>();

    public Product() {
    }

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
