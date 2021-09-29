/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 17-Aug-21
 *   Time: 6:00 PM
 *   File: StockItemFullReport.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockItemFullReport {
    private String totalStockValue;
    private List<StockItemReportModel> stockItemReportModels;

    public StockItemFullReport(String totalStockValue, List<StockItemReportModel> stockItemReportModels) {
        this.totalStockValue = totalStockValue;
        this.stockItemReportModels = stockItemReportModels;
    }
    public StockItemFullReport(){}
}

