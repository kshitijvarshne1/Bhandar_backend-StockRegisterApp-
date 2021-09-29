/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 23-Jul-21
 *   Time: 5:31 PM
 *   File: ItemTransactionReport.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemTransactionReport {
    private int openingStock;
    private int closingStock;
    private String totalStockIn;
    private String totalStockOut;
    private int amountTotalStockIn;
    private int amountTotalStockOut;
    private List<StockTransactionModel> Transactions;

    public ItemTransactionReport(int openingStock, int closingStock, String totalStockIn, String totalStockOut, int amountTotalStockIn, int amountTotalStockOut, List<StockTransactionModel> transactions) {
        this.openingStock = openingStock;
        this.closingStock = closingStock;
        this.totalStockIn = totalStockIn;
        this.totalStockOut = totalStockOut;
        this.amountTotalStockIn = amountTotalStockIn;
        this.amountTotalStockOut = amountTotalStockOut;
        Transactions = transactions;
    }
    public ItemTransactionReport() {
        this.openingStock = 0;
        this.closingStock = 0;
        this.totalStockIn = String.valueOf(0);
        this.totalStockOut = String.valueOf(0);
        this.amountTotalStockIn = 0;
        this.amountTotalStockOut = 0;
        Transactions = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ItemTransactionReport{" +
                "openingStock=" + openingStock +
                ", closingStock=" + closingStock +
                ", totalStockIn='" + totalStockIn + '\'' +
                ", totalStockOut='" + totalStockOut + '\'' +
                ", amountTotalStockIn=" + amountTotalStockIn +
                ", amountTotalStockOut=" + amountTotalStockOut +
                ", Transactions=" + Transactions +
                '}';
    }
}

