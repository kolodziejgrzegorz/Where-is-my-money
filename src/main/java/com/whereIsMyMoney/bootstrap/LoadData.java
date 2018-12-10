package com.whereIsMyMoney.bootstrap;

import com.whereIsMyMoney.dao.*;
import com.whereIsMyMoney.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Profile("h2")
public class LoadData implements ApplicationListener<ContextRefreshedEvent> {

    private final ProductDao productDao;
    private final PurchaseDao purchaseDao;
    private final BillDao billDao;
    private final ShopDao shopDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    public LoadData(ProductDao productDao, PurchaseDao purchaseDao,
                    BillDao billDao, ShopDao shopDao, CategoryDao categoryDao, UserDao userDao) {
        this.productDao = productDao;
        this.purchaseDao = purchaseDao;
        this.billDao = billDao;
        this.shopDao = shopDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

      loadBills();

    }

    private void loadBills(){
        User user = new User("name", "email", "password");
        User u1 = userDao.save(user);

        Shop shop1 = new Shop();
        shop1.setName("Lewiatan");
        Shop shop2 = new Shop();
        shop2.setName("Biedronka");

        Shop s1 = shopDao.save(shop1);
        Shop s2 = shopDao.save(shop2);

        Category category1 = new Category("spożywcze");
        Category c1 = categoryDao.save(category1);
        Category category2 = new Category("środki czystości");
        Category c2 = categoryDao.save(category2);

        Product p1 = new Product("jabłko", c1);
        Product p2 = new Product("gruszka", c1);
        Product p3 = new Product("pomarańcza", c1);
        Product p4 = new Product("szampon", c2);
        Product p5 = new Product("pasta do zębów", c2);
        Product p6 = new Product("mydło", c2);
        List<Product> productList = Arrays.asList(p1, p2, p3, p4, p5, p6);
        productDao.saveAll(productList);

        Bill b1 = new Bill(LocalDate.now(), s1, u1);
        addPurchaseToBill(b1, p1, 0.5, 1.0 );
        addPurchaseToBill(b1, p2, 2.4, 3.0 );
        addPurchaseToBill(b1, p3, 3.1, 2.0 );

        Bill b2 = new Bill(LocalDate.now(), s1, u1);
        addPurchaseToBill(b2, p2, 2.5, 2.99 );
        addPurchaseToBill(b2, p3, 1.2, 3.0 );
        addPurchaseToBill(b2, p4, 1.0, 6.0 );

        Bill b3 = new Bill(LocalDate.now(), s2, u1);
        addPurchaseToBill(b3, p4, 1.0, 7.0 );
        addPurchaseToBill(b3, p5, 1.0, 4.0 );
        addPurchaseToBill(b3, p6, 1.0, 2.0 );

        billDao.save(b1);
        billDao.save(b2);
        billDao.save(b3);

    }

    private Purchase addPurchaseToBill(Bill bill, Product product, Double productQuantity, Double productPrice){
        Purchase purchase = new Purchase(productQuantity, productPrice, bill, product);
        purchase.setSum(productPrice*productQuantity);
        bill.getPurchases().add(purchase);
        bill.setSum(bill.getSum() + purchase.getSum());
        return purchase;
    }

}
