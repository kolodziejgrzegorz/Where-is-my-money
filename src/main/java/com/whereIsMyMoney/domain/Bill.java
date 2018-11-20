package com.whereIsMyMoney.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "sum", nullable = false)
    private Double sum = -1.0;

    @ManyToOne
    @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    public Bill() {
    }

    public Bill(LocalDate date, Double sum, Shop shop, User user) {
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
