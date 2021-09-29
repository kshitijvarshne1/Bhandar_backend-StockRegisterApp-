/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:48 AM
 *   File: ItemTransactionReportV2.java
 */

package com.stockregisterapp.v2API.model;

import com.stockregisterapp.model.StockTransactionModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemTransactionReportV2 {
    private String openingStock;
    private String closingStock;
    private String totalStockIn;
    private String totalStockOut;
    private String amountTotalStockIn;
    private String amountTotalStockOut;
    private List<StockTransactionModelV2> Transactions;

    public ItemTransactionReportV2(String openingStock, String closingStock, String totalStockIn, String totalStockOut, String amountTotalStockIn, String amountTotalStockOut, List<StockTransactionModelV2> transactions) {
        this.openingStock = openingStock;
        this.closingStock = closingStock;
        this.totalStockIn = totalStockIn;
        this.totalStockOut = totalStockOut;
        this.amountTotalStockIn = amountTotalStockIn;
        this.amountTotalStockOut = amountTotalStockOut;
        Transactions = transactions;
    }

    public ItemTransactionReportV2() {

    }
}

