package com.whereIsMyMoney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="product")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Product  implements BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
//    @JsonIgnoreProperties("id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases = new ArrayList<>();


    public Product() {
    }

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
