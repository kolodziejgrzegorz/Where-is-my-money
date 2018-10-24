package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.model.Bill;
import com.whereIsMyMoney.model.User;
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

    public User getOneByName(String name){ return userDao.findByName(name);}

    public User add(User theUser){
        return userDao.save(theUser);
    }

    public User update(User theUser){
        return userDao.save(theUser);
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
