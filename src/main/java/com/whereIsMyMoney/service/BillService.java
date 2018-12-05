package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.BillMapper;
import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.dao.BillDao;
import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.User;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillService {

    private final BillDao billDao;
    private final BillMapper billMapper;

    public BillService(BillDao billDao, BillMapper billMapper) {
        this.billDao = billDao;
        this.billMapper = billMapper;
    }

    public List<BillDto> findAll(){
        List<BillDto> billDtos = new ArrayList<>();

        billDtos = billDao.findAll().stream()
                .map(billMapper::billToBillDto)
                .collect(Collectors.toList());

        if( billDtos.isEmpty() ) {
            throw new DataNotFoundException("Bills list not found");
        }
        return billDtos;
    }

    public BillDto findById(Long id){
        Optional<Bill> billOptional = billDao.findById(id);

        if(!billOptional.isPresent()){
            throw new DataNotFoundException("Not Found Bill with id : " + id);
        }

        return billMapper.billToBillDto(billOptional.get());
    }

    public List<BillDto> findByUserId(Long id){
        List<BillDto> billDtos = new ArrayList<>();

        billDtos = billDao.findByUserId(id).stream()
                .map(billMapper::billToBillDto)
                .collect(Collectors.toList());

        if( billDtos.isEmpty() ) {
            throw new DataNotFoundException("Not Found Bill with user id:" + id);
        }
        return billDtos;
    }

    public BillDto addNew(BillDto theBillDto){
        Bill newBill = billMapper.billDtoToBill(theBillDto);

        return billMapper.billToBillDto(billDao.save(newBill));
    }

    public BillDto update(Long id, BillDto theBillDto){
        Bill updatedBill = billMapper.billDtoToBill(theBillDto);
        updatedBill.setId(id);

        return billMapper.billToBillDto(billDao.save(updatedBill));
    }

    public void delete(Long id){
        billDao.deleteById(id);
    }

    public void deleteByUser(User user){
        List<Bill> bills = billDao.findByUserName(user.getName());
        billDao.deleteAll(bills);
    }
}
