package com.stockregisterapp.v2API.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockTransactionModelRequestV2 {

    private String stockItemId;
    private String transactionTs;
    private String txnType;
    private String preTxnStockCount;
    private String postTxnStockCount;
    private Double txnUnitPrice;
    private String stockTxnEntity;
    private String sourceTxnEntityId;
    private String sourceEntityName;
    private boolean isAutoTransfer;

    public StockTransactionModelRequestV2(
            String stockItemId,
            String transactionTs,
            String txnType,
            String preTxnStockCount,
            String postTxnStockCount,
            Double txnUnitPrice,
            String stockTxnEntity,
            String sourceTxnEntityId,
            String sourceEntityName,
            boolean isAutoTransfer
    ) {
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

    public StockTransactionModelRequestV2(
            String stockItemId,
            String transactionTs,
            String txnType,
            String preTxnStockCount,
            String postTxnStockCount,
            Double txnUnitPrice,
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

