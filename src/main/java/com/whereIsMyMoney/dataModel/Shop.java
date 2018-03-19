package com.whereIsMyMoney.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="shop")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Shop  implements BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;
    @Column(name="name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private List<Bill> bills = new ArrayList<>();

    public Shop() {
    }

    public Shop(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(name, shop.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
