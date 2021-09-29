/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Aug-21
 *   Time: 7:01 PM
 *   File: StockItems.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemRequest {
    private String name;
    private double purchasePrice;
    private double sellingPrice;
    private double mrp;
    private String priceUnit;
    private double availableCount;
    private String stockAddDate;

    public StockItemRequest(String name, double purchasePrice, double sellingPrice, double mrp, String priceUnit, double availableCount, String stockAddDate) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.priceUnit = priceUnit;
        this.availableCount = availableCount;
        this.stockAddDate = stockAddDate;
    }
}

