package com.stockregisterapp.repository;



import com.stockregisterapp.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockItemRepository extends JpaRepository<StockItem, String> {
    @Query("select s from StockItem s where s.storeId=:storeId")
    List<StockItem> findByStoreId(@Param("storeId") String storeId);

    @Transactional
    @Modifying
    @Query("update StockItem s set s.name=:name, s.purchasePrice=:purchasePrice, s.sellingPrice=:sellingPrice, s.mrp=:mrp, s.priceUnit=:priceUnit, s.updatedAt=:todayDT where s.stockItemId=:stockItemId")
    int updateStockItem(String stockItemId, String name, String purchasePrice, String sellingPrice, String mrp, String priceUnit, String todayDT);


    @Transactional
    @Modifying
    @Query("update StockItem  s set s.purchasePrice=:price where s.stockItemId=:StockItemId")
    void updateTransactionPurchase(String StockItemId, String price);


    @Transactional
    @Modifying
    @Query("update StockItem  s set s.sellingPrice=:price where s.stockItemId=:StockItemId")
    void updateTransactionSelling(String StockItemId, String price);

    @Transactional
    @Modifying
    @Query("update StockItem  s set s.availableCount=:availableCount where s.stockItemId=:stockItemId")
    void updateAvailableCount(String stockItemId, int availableCount);

    @Transactional
    @Modifying
    @Query("update StockItem  s set s.availableCountF=:availableCountF where s.stockItemId=:stockItemId")
    void updateAvailableCountF(String stockItemId, double availableCountF);




}
