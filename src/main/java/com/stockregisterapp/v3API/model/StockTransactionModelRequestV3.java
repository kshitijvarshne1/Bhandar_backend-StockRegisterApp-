/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:44 PM
 *   File: StockTransactionModelRequestV3.java
 */

package com.stockregisterapp.v3API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTransactionModelRequestV3 {
    private String stockItemId;
    private String txnType;
    private double openingStock;
    private double closingStock;
    private String destinationTxnEntity;
    private String destinationTxnEntityId;

    public StockTransactionModelRequestV3(String stockItemId, String txnType, double openingStock, double closingStock, String destinationTxnEntity, String destinationTxnEntityId) {
        this.stockItemId = stockItemId;
        this.txnType = txnType;
        this.openingStock = openingStock;
        this.closingStock = closingStock;
        this.destinationTxnEntity = destinationTxnEntity;
        this.destinationTxnEntityId = destinationTxnEntityId;
    }
}

