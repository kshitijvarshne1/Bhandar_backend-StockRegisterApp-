/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:49 AM
 *   File: StockTransactionModelV2.java
 */

package com.stockregisterapp.v2API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTransactionModelV2 {
    private String txnId;
    private String transactionTs;
    private String txnType;
    private String stockCount ;
    private String txnUnitPrice;
    private String totalAmount;
    private String availableStock;
    private String stockTxnEntity;
    private String sourceTxnEntityName;
    private String preTxnStockCount;
    private String postTxnStockCount;
    private String sourceTxnEntityId;

    public StockTransactionModelV2(String txnId, String transactionTs, String txnType, String stockCount, String txnUnitPrice, String totalAmount, String availableStock, String stockTxnEntity, String sourceTxnEntityName, String preTxnStockCount, String postTxnStockCount, String sourceTxnEntityId) {
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

