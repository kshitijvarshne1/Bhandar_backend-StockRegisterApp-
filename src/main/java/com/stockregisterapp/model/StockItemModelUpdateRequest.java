/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 22-Jul-21
 *   Time: 2:06 PM
 *   File: StockItemModelUpdateRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemModelUpdateRequest {
    private String stockItemId;
    private String name;
    private String purchasePrice;
    private String sellingPrice;
    private String mrp;
    private String priceUnit;

    public StockItemModelUpdateRequest(String stockItemId, String name, String purchasePrice, String sellingPrice, String mrp, String priceUnit) {
        this.stockItemId = stockItemId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.priceUnit = priceUnit;
    }
}

