/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Aug-21
 *   Time: 2:36 PM
 *   File: ItemTransactionModelV2.java
 */

package com.stockregisterapp.v2API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTransactionModelV2 {
    private String itemName;
    private String itemId;
    private String totalIn;
    private String totalOut;
    private String openingStock;
    private String closingStock;
    private String priceUnit;
    private String currentStockCount;
    private String sellingPrice;
    private String purchasePrice;
    private String lastUpdatedTime;
    private String totalInPrice;
    private String totalOutPrice;

    public ItemTransactionModelV2(String itemName, String itemId, String totalIn, String totalOut, String openingStock, String closingStock, String priceUnit, String currentStockCount, String sellingPrice, String purchasePrice, String lastUpdatedTime, String totalInPrice, String totalOutPrice) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.openingStock = openingStock;
        this.closingStock = closingStock;
        this.priceUnit = priceUnit;
        this.currentStockCount = currentStockCount;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.lastUpdatedTime = lastUpdatedTime;
        this.totalInPrice = totalInPrice;
        this.totalOutPrice = totalOutPrice;
    }

    public ItemTransactionModelV2() {

    }
}

