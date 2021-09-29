/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Aug-21
 *   Time: 8:36 PM
 *   File: UpdateTransactionRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransactionRequest {
    private String txnId;
    private String stockItemId;
    private String transactionTs;
    private String txnType;
    private String preTxnStockCount;
    private String postTxnStockCount;
    private String txnUnitPrice;
    private String stockTxnEntity;
    private String sourceTxnEntityId;
    private String sourceEntityName;


    public UpdateTransactionRequest(String txnId, String stockItemId, String transactionTs, String txnType, String preTxnStockCount, String postTxnStockCount, String txnUnitPrice, String stockTxnEntity, String sourceTxnEntityId, String sourceEntityName) {
        this.txnId = txnId;
        this.stockItemId = stockItemId;
        this.transactionTs = transactionTs;
        this.txnType = txnType;
        this.preTxnStockCount = preTxnStockCount;
        this.postTxnStockCount = postTxnStockCount;
        this.txnUnitPrice = txnUnitPrice;
        this.stockTxnEntity = stockTxnEntity;
        this.sourceTxnEntityId = sourceTxnEntityId;
        this.sourceEntityName = sourceEntityName;
    }
}

