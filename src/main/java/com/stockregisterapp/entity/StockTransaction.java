/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 11:25 AM
 *   File: StockTransaction.java
 */

package com.stockregisterapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class StockTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String txnId;
    private String transactionTs;
    private String stockItemId;
    private String txnType;
    private String preTxnStockCount;
    private String postTxnStockCount;
    private int txnUnitPrice;
    private String stockTxnEntity;
    private String sourceTxnEntityId;
    private String sourceEntityName;
    private double txnUnitPriceF;

    public StockTransaction(
            String transactionTs,
            String stockItemId,
            String txnType,
            String preTxnStockCount,
            String postTxnStockCount,
            int txnUnitPrice,
            String stockTxnEntity,
            String sourceTxnEntityId,
            String sourceEntityName,
            double txnUnitPriceF
    ) {
        this.transactionTs = transactionTs;
        this.stockItemId = stockItemId;
        this.txnType = txnType;
        this.preTxnStockCount = preTxnStockCount;
        this.postTxnStockCount = postTxnStockCount;
        this.txnUnitPrice = txnUnitPrice;
        this.stockTxnEntity = stockTxnEntity;
        this.sourceTxnEntityId = sourceTxnEntityId;
        this.sourceEntityName= sourceEntityName;
        this.txnUnitPriceF = txnUnitPriceF;
    }

    public StockTransaction() {}
}

