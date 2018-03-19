package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.ShopDao;
import com.whereIsMyMoney.dataModel.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopDao shopDao;

    public List<Shop> getAll(){
        return shopDao.findAll();
    }

    public Shop getOne(int id){
        return shopDao.getOne(id);
    }

    public void add(Shop theShop){
        shopDao.save(theShop);
    }

    public void update(Shop theShop){
        shopDao.save(theShop);
    }

    public void delete(Shop theShop){
        onDeleteSetMessage(theShop);
        //shopDao.delete(theShop);
    }
    public void delete(int id){
        onDeleteSetMessage(shopDao.findOne(id));
        //shopDao.delete(id);
    }
    public boolean exists(int id){
        return shopDao.exists(id);
    }

    private void onDeleteSetMessage(Shop theShop){
        theShop.setName("Shop deleted");
        shopDao.save(theShop);
    }
}
