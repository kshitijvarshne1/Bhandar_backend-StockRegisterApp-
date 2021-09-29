/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:34 PM
 *   File: StockItemFullReportV2.java
 */

package com.stockregisterapp.v2API.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockItemFullReportV2 {
    private String totalStockValue;
    private List<StockItemReportModelV2> stockItemReportModels;

    public StockItemFullReportV2(String totalStockValue, List<StockItemReportModelV2> stockItemReportModels) {
        this.totalStockValue = totalStockValue;
        this.stockItemReportModels = stockItemReportModels;
    }

    public StockItemFullReportV2() {

    }
}

