package com.whereIsMyMoney.service;

import com.whereIsMyMoney.dao.UserDao;
import com.whereIsMyMoney.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final BillService billService;

    public UserService(UserDao userDao, BillService billService) {
        this.userDao = userDao;
        this.billService = billService;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Long id) {
        return userDao.getOne(id);
    }

    public User findByName(String name) {
        return userDao.findByName(name);
    }

    public User addNew(User theUser) {
        return userDao.save(theUser);
    }

    public User update(User theUser) {
        return userDao.save(theUser);
    }

    public void delete(User theUser) {
        onDeleteAction(theUser);
        userDao.delete(theUser);
    }

    public void delete(Long id) {
        onDeleteAction(findById(id));
        userDao.deleteById(id);
    }

    public boolean exists(Long id) {
        return userDao.existsById(id);
    }

    private void onDeleteAction(User theUser) {
        billService.deleteByUser(theUser);
    }
}
