/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 10:27 PM
 *   File: StockItemModelRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockItemModelRequest {
    private String stockItemId;
    private String name;
    private String purchasePrice;
    private String sellingPrice;
    private String mrp;
    private String priceUnit;
    private List<StockStoreDetails> stockStoreDetails;

    public StockItemModelRequest(String stockItemId, String name, String purchasePrice, String sellingPrice, String mrp, String priceUnit, List<StockStoreDetails> stockStoreDetails) {
        this.stockItemId = stockItemId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.priceUnit = priceUnit;
        this.stockStoreDetails = stockStoreDetails;
    }
}

