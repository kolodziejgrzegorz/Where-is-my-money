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
    private final ShopService shopService;
    private final PurchaseService purchaseService;
    private final UserService userService;
    private final BillMapper billMapper;

    public BillService(BillDao billDao, ShopService shopService,
                       PurchaseService purchaseService, UserService userService, BillMapper billMapper) {
        this.billDao = billDao;
        this.shopService = shopService;
        this.purchaseService = purchaseService;
        this.userService = userService;
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

    public List<BillDto> getByUserId(Long id){
        List<BillDto> billDtos = new ArrayList<>();

        billDtos = billDao.findByUserId(id).stream()
                .map(billMapper::billToBillDto)
                .collect(Collectors.toList());

        if( billDtos.isEmpty() ) {
            throw new DataNotFoundException("Bills list not found");
        }
        return billDtos;
    }

    public BillDto addNew(BillDto theBillDto){
        Bill bill = new Bill();

        bill = billMapper.billDtoToBill(theBillDto);


        return null;
    }

    public BillDto update(Long id, BillDto theBillDto){
        Bill updatedBill = billMapper.billDtoToBill(theBillDto);
        updatedBill.setId(id);

//        setUserToBill(theBill);
//        setShopToBill(theBill);
//        theBill.setSum(sumCalculator(theBill));
        return billMapper.billToBillDto(billDao.save(updatedBill));
    }

    public void delete(Bill theBill){
//        deletePurchasesByBillId(theBill);
        billDao.delete(theBill);
    }
    public void delete(Long id){
//      when delete bill delete also all his list
//        deletePurchasesByBillId(getOne(id));
        billDao.deleteById(id);
    }

//    private void setShopToBill(BillDto theBillDto){
//        String shopName = theBillDto.getShopName();
//        if(shopService.findByName(shopName) == null){
//            throw new DataNotFoundException("Not found shop with name " + shopName);
//        }
//        ShopDto shopDto = shopService.findByName(shopName);
//        theBill.setShop(shop);
//    }

    private void setUserToBill(Bill theBill){
        String userName = theBill.getUser().getName();
        User user = userService.findByName(userName);
        theBill.setUser(user);
    }

//    private void deletePurchasesByBillId(Bill theBill){
//        List<Purchase> purchases = purchaseService.findByBillId(theBill.getId());
//        purchaseService.delete(purchases);
//    }
//
//    private Double sumCalculator(BillDto theBillDto){
//        Double sum = 0.0;
//        List<Purchase> purchases = purchaseService.findByBillId(theBillDto.getId());
//        for(Purchase p : purchases){
//            sum += p.getSum();
//        }
//        return sum;
//    }
}
