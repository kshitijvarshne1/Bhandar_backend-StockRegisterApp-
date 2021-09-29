/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 2:35 PM
 *   File: StoreReportModel.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.hql.internal.ast.util.TokenPrinters;

import java.util.List;

@Getter
@Setter
public class StoreReportModel {
    private int totalStockIn;
    private int totalStockOut;
    private List<ItemTransactionModel> ItemTransactions;

    public StoreReportModel(int totalStockIn, int totalStockOut, List<ItemTransactionModel> itemTransactions) {
        this.totalStockIn = totalStockIn;
        this.totalStockOut = totalStockOut;
        ItemTransactions = itemTransactions;
    }
}

