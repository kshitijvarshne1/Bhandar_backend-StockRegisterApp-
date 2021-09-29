/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 10:29 PM
 *   File: StockItemReportModel.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemReportModel {
    private String stockItemId;
    private String storeId;
    private String name;
    private String purchasePrice;
    private String sellingPrice;
    private String mrp;
    private String priceUnit;
    private int availableCount;
    private String lastUpdatedTime;

    public StockItemReportModel(String stockItemId, String storeId, String name, String purchasePrice, String sellingPrice, String mrp, String priceUnit, int availableCount, String lastUpdatedTime) {
        this.stockItemId = stockItemId;
        this.storeId = storeId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.priceUnit = priceUnit;
        this.availableCount = availableCount;
        this.lastUpdatedTime=lastUpdatedTime;
    }
}

