/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 2:37 PM
 *   File: ItemTransactionModel.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTransactionModel {
    private String itemName;
    private String itemId;
    private int totalIn;
    private int totalOut;
    private String openingStock;
    private String closingStock;
    private String priceUnit;
    private int currentStockCount;
    private int sellingPrice;
    private int purchasePrice;
    private String lastUpdatedTime;
    private long totalInPrice;
    private long totalOutPrice;

    public ItemTransactionModel(String itemName, String itemId, int totalIn, int totalOut, String openingStock, String closingStock, String priceUnit, int currentStockCount, int sellingPrice, int purchasePrice, String lastUpdatedTime, long totalInPrice, long totalOutPrice) {
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

    public ItemTransactionModel() {

    }
}

