/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 2:30 PM
 *   File: StockTransactionModelRequest.java
 */

package com.stockregisterapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockTransactionModelRequest {
    private String stockItemId;
    private String transactionTs;
    private String txnType;
    private String preTxnStockCount;
    private String postTxnStockCount;
    private int txnUnitPrice;
    private String stockTxnEntity;
    private String sourceTxnEntityId;
    private String sourceEntityName;
    private boolean isAutoTransfer;


    public StockTransactionModelRequest(
            String stockItemId,
            String transactionTs,
            String txnType,
            String preTxnStockCount,
            String postTxnStockCount,
            int txnUnitPrice,
            String stockTxnEntity,
            String sourceTxnEntityId,
            String sourceEntityName,
            boolean isAutoTransfer) {
        this.stockItemId = stockItemId;
        this.transactionTs = transactionTs;
        this.txnType = txnType;
        this.preTxnStockCount = preTxnStockCount;
        this.postTxnStockCount = postTxnStockCount;
        this.txnUnitPrice = txnUnitPrice;
        this.stockTxnEntity = stockTxnEntity;
        this.sourceTxnEntityId = sourceTxnEntityId;
        this.sourceEntityName= sourceEntityName;
        this.isAutoTransfer = isAutoTransfer;
    }

    public StockTransactionModelRequest(
            String stockItemId,
            String transactionTs,
            String txnType,
            String preTxnStockCount,
            String postTxnStockCount,
            int txnUnitPrice,
            String stockTxnEntity,
            String sourceTxnEntityId,
            String sourceEntityName
    ) {
        this(stockItemId,
                transactionTs,
                txnType,
                preTxnStockCount,
                postTxnStockCount,
                txnUnitPrice,
                stockTxnEntity,
                sourceTxnEntityId,
                sourceEntityName,
                false
        );
    }
}

