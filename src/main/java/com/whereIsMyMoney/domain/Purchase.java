package com.whereIsMyMoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="purchase")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "product_price", nullable = false)
    private double productPrice;

    @Column(name = "sum", nullable = false)
    private double sum;


    //@JsonManagedReference
    @JsonIgnoreProperties({"date", "sum","user","shop","product","purchases" })
    @ManyToOne
    @JoinColumn(name="bill_id", nullable=false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Purchase() {
    }

    public Purchase(int id, int productQuantity, double productPrice, double sum, Bill theBill, String product_name) {
        this.id = id;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.sum = sum;
        this.bill = theBill;
        this.product.setName(product_name);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                ", sum=" + sum +
                ", bill_id=" + bill.getId() +
                ", product=" + product.getName() +
                '}';
    }
}
