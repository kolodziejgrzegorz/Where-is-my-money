package com.whereIsMyMoney.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="bill")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "sum", nullable = false)
    private double sum = -1;

    @ManyToOne
    @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    public Bill() {
    }

    public Bill(Date date, int sum, Shop shop, User user) {
        this.date = date;
        this.sum = sum;
        this.shop = shop;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id = " + id +
                ", date = " + date +
                ", sum = " + sum +
                ", shop = " + shop.getName() +
                ", user = " + user.getName() +
                ", purchases" + purchases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
