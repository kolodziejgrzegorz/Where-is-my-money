package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.dataModel.Bill;
import com.whereIsMyMoney.dataModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    BillDao billDao;

    public List<User> getAll(){
        return userDao.findAll();
    }

    public User getOne(int id){
        return userDao.getOne(id);
    }

    public void add(User theUser){
        userDao.save(theUser);
    }

    public void update(User theUser){
        userDao.save(theUser);
    }

    public void delete(User theUser){
        onDeleteAction(theUser);
        userDao.delete(theUser);
    }
    public void delete(int id){
        onDeleteAction(getOne(id));
        userDao.delete(id);
    }
    public boolean exists(int id){
        return userDao.exists(id);
    }

    private void onDeleteAction(User theUser){
        List<Bill> bills = billDao.findByUserName(theUser.getName());
        billDao.delete(bills);
    }
}
