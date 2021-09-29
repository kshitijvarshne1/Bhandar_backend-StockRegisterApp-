/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:34 PM
 *   File: StockItemReportModelV2.java
 */

package com.stockregisterapp.v2API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemReportModelV2 {
    private String stockItemId;
    private String storeId;
    private String name;
    private double purchasePrice;
    private double sellingPrice;
    private double mrp;
    private String priceUnit;
    private double availableCount;
    private String lastUpdatedTime;

    public StockItemReportModelV2(String stockItemId,
                                  String storeId,
                                  String name,
                                  double purchasePrice,
                                  double sellingPrice,
                                  double mrp,
                                  String priceUnit,
                                  double availableCount,
                                  String lastUpdatedTime
    ) {
        this.stockItemId = stockItemId;
        this.storeId = storeId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.priceUnit = priceUnit;
        this.availableCount = availableCount;
        this.lastUpdatedTime = lastUpdatedTime;
    }
}

