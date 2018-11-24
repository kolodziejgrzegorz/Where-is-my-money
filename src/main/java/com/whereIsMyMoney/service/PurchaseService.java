package com.whereIsMyMoney.service;

import com.whereIsMyMoney.api.mapper.PurchaseMapper;
import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.dao.PurchaseDao;
import com.whereIsMyMoney.domain.Purchase;
import com.whereIsMyMoney.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseDao purchaseDao;
    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseDao purchaseDao, PurchaseMapper purchaseMapper) {
        this.purchaseDao = purchaseDao;
        this.purchaseMapper = purchaseMapper;
    }

    public List<PurchaseDto> findAll() {
        List<PurchaseDto> purchaseListDto = new ArrayList<>();

        purchaseListDto = purchaseDao.findAll().stream()
                .map(purchaseMapper::purchaseToPurchaseDto)
                .collect(Collectors.toList());
        if (purchaseListDto.isEmpty()) {
            throw new DataNotFoundException("Purchases list not found");
        }
        return purchaseListDto;
    }

    public PurchaseDto findById(Long id) {
        Optional<Purchase> purchaseOptional = purchaseDao.findById(id);
        if (!purchaseOptional.isPresent()) {
            throw new DataNotFoundException("Not found purchase with id: " + id);
        }
        return purchaseMapper.purchaseToPurchaseDto(purchaseOptional.get());
    }

    public List<PurchaseDto> findByBillId(Long id) {
        List<PurchaseDto> purchaseListDto = new ArrayList<>();

        purchaseListDto = purchaseDao.findByBillId(id).stream()
                .map(purchaseMapper::purchaseToPurchaseDto)
                .collect(Collectors.toList());
        if (purchaseListDto.isEmpty()) {
            throw new DataNotFoundException("Purchases list not found for bill with id: " + id);
        }
        return purchaseListDto;
    }
// logic move to billService
//    public PurchaseDto addNew(Long billId, PurchaseDto thePurchaseDto) {
//
//        Optional<Product> product = productDao.findById(thePurchaseDto.getProduct_id());
//        if(!product.isPresent()){
//            Product product1 = new Product();
//            product1.setId(thePurchaseDto.getProduct_id());
//        }
//
//        return purchaseDao.save(thePurchase);
//    }
//
//    public PurchaseDto update(Long billId, Purchase thePurchase) {
//        setProductToPurchase(thePurchase);
//        thePurchase.setSum(thePurchase.getProductQuantity() * thePurchase.getProductPrice());
//        return purchaseDao.save(thePurchase);
//    }
//
//    public void delete(List<PurchaseDto> purchasesDtoList) {
//        List<Purchase> list = purchasesDtoList.stream()
//                .map(purchaseMapper::purchaseDtoToPurchase)
//                .collect(Collectors.toList());
//
//        purchaseDao.deleteAll(list);
//    }

    public void delete(Long id) {
        purchaseDao.deleteById(id);
    }
}
