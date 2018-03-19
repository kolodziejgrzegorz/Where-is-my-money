package com.whereIsMyMoney.dataModel;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name="purchase")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Purchase  implements BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;
    @Column(name = "product_quantity", nullable = false)
    private int product_quantity;
    @Column(name = "product_price", nullable = false)
    private double product_price;
    @Column(name = "sum", nullable = false)
    private double sum;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="bill_id", nullable=false)
    private Bill bill;

    @OneToOne
    @JoinColumn(name = "product_id")
//    @JsonIgnoreProperties("id")
    private Product product;

    public Purchase() {
    }

    public Purchase(int id, int product_quantity, double product_price, double sum, Bill bill, Product product) {
        this.id = id;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.sum = sum;
        this.bill = bill;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }


}
