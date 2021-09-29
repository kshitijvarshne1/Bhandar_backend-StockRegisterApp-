/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 06-Sep-21
 *   Time: 1:26 PM
 *   File: PartyTransactionReport.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyTransactionReport {
    private String itemName;
    private String txnDateTime;
    private String stockQuantity;
    private String stockPrice;
    private String stockValue;
    private String txnType;

    public PartyTransactionReport(
            String itemName,
            String txnDateTime,
            String stockQuantity,
            String stockPrice,
            String stockValue,
            String txnType
    ) {
        this.itemName = itemName;
        this.txnDateTime = txnDateTime;
        this.stockQuantity = stockQuantity;
        this.stockPrice = stockPrice;
        this.stockValue = stockValue;
        this.txnType = txnType;
    }
}

