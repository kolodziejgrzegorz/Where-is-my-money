package com.whereIsMyMoney.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @Column(name = "sum", nullable = false)
    private Double sum;

    @ManyToOne
    @JoinColumn(name="bill_id", nullable=false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Purchase() {
    }

    public Purchase(Long id, Integer productQuantity, Double productPrice, Double sum, Bill theBill, String product_name) {
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
