/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 23-Jul-21
 *   Time: 5:38 PM
 *   File: StockTransactionModel.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTransactionModel {
    private String txnId;
    private String transactionTs;
    private String txnType;
    private String stockCount ;
    private int txnUnitPrice;
    private int totalAmount;
    private String availableStock;
    private String stockTxnEntity;
    private String sourceTxnEntityName;
    private String preTxnStockCount;
    private String postTxnStockCount;
    private String sourceTxnEntityId;

    public StockTransactionModel(String txnId, String transactionTs, String txnType, String stockCount, int txnUnitPrice, int totalAmount, String availableStock, String stockTxnEntity, String sourceTxnEntityName, String preTxnStockCount, String postTxnStockCount, String sourceTxnEntityId) {
        this.txnId = txnId;
        this.transactionTs = transactionTs;
        this.txnType = txnType;
        this.stockCount = stockCount;
        this.txnUnitPrice = txnUnitPrice;
        this.totalAmount = totalAmount;
        this.availableStock = availableStock;
        this.stockTxnEntity = stockTxnEntity;
        this.sourceTxnEntityName = sourceTxnEntityName;
        this.preTxnStockCount = preTxnStockCount;
        this.postTxnStockCount = postTxnStockCount;
        this.sourceTxnEntityId = sourceTxnEntityId;
    }
}

