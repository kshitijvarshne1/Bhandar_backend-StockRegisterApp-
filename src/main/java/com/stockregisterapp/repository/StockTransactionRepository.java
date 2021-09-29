package com.stockregisterapp.repository;


import com.stockregisterapp.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, String> {

    @Query("select s from StockTransaction s where s.stockItemId= :stockItemId")
    List<StockTransaction> findByStockItemId(@Param("stockItemId") String stockItemId);


    @Query("select s from StockTransaction s where s.stockItemId IN (:itemIds)")
    List<StockTransaction> findByStockItemIds(@Param("itemIds") List<String> itemIds);

    @Transactional
    @Modifying
    @Query("update StockTransaction  s set s.transactionTs=:transactionTs,s.txnType=:txnType,s.preTxnStockCount=:preTxnStockCount, s.postTxnStockCount=:postTxnStockCount, s.txnUnitPrice=:txnUnitPrice,  s.txnUnitPriceF=:txnUnitPriceF, s.stockTxnEntity=:stockTxnEntity, s.sourceTxnEntityId=:sourceTxnEntityId, s.sourceEntityName=:sourceEntityName where s.txnId=:txnId and s.stockItemId=:stockItemId")
    void updateTransaction(
            String txnId,
            String stockItemId,
            String transactionTs,
            String txnType,
            String preTxnStockCount,
            String postTxnStockCount,
            int txnUnitPrice,
            String stockTxnEntity,
            String sourceTxnEntityId,
            String sourceEntityName,
            double txnUnitPriceF
    );

    @Query("select s from StockTransaction  s where s.sourceTxnEntityId=:partyId")
    List<StockTransaction> findByPartyId(String partyId);
}


