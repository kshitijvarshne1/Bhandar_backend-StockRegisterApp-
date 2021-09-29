/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Aug-21
 *   Time: 2:30 PM
 *   File: StoreReportModel.java
 */

package com.stockregisterapp.v2API.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreReportModelV2 {
    private String totalStockIn;
    private String totalStockOut;
    private List<ItemTransactionModelV2> ItemTransactions;

    public StoreReportModelV2(String totalStockIn, String totalStockOut, List<ItemTransactionModelV2> itemTransactions) {
        this.totalStockIn = totalStockIn;
        this.totalStockOut = totalStockOut;
        ItemTransactions = itemTransactions;
    }
}

