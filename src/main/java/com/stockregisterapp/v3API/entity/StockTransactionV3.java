/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:06 PM
 *   File: StockTransactionV3.java
 */

package com.stockregisterapp.v3API.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class StockTransactionV3 {
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
    private double openingStock;
    private double closingStock;
    private String destinationTxnEntity;
    private String destinationTxnEntityId;

    public StockTransactionV3(String transactionTs, String stockItemId, String txnType, double openingStock, double closingStock, String destinationTxnEntity, String destinationTxnEntityId) {
        this.transactionTs = transactionTs;
        this.stockItemId = stockItemId;
        this.txnType = txnType;
        this.openingStock = openingStock;
        this.closingStock = closingStock;
        this.destinationTxnEntity = destinationTxnEntity;
        this.destinationTxnEntityId = destinationTxnEntityId;
    }

    public StockTransactionV3() {

    }
}

