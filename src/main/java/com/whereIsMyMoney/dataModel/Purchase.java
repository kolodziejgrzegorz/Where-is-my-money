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

    public Purchase(int id, int productQuantity, double productPrice, double sum, Bill bill, Product product) {
        this.id = id;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.sum = sum;
        this.bill = bill;
        this.product = product;
    }

    public Purchase(int id, int productQuantity, double productPrice, double sum, Bill theBill, String product_name) {
        this.id = id;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.sum = sum;
        this.bill = theBill;
        this.product.setName(product_name);
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
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
