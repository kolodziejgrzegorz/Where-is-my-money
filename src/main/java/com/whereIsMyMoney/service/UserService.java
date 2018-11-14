package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.model.Bill;
import com.whereIsMyMoney.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final BillDao billDao;

    public UserService(UserDao userDao, BillDao billDao) {
        this.userDao = userDao;
        this.billDao = billDao;
    }

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
        userDao.deleteById(id);
    }
    public boolean exists(int id){
        return userDao.existsById(id);
    }

    private void onDeleteAction(User theUser){
        List<Bill> bills = billDao.findByUserName(theUser.getName());
        billDao.deleteAll(bills);
    }
}
