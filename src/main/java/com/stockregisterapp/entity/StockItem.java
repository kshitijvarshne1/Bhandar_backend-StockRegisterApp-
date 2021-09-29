/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 11:17 AM
 *   File: StockItem.java
 */

package com.stockregisterapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
@Getter
@Setter
public class StockItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String stockItemId;
    private String storeId;
    private String name;
    private String purchasePrice;
    private String sellingPrice;
    private String mrp;
    private String priceUnit;
    private int availableCount;
    private String createdAt;
    private String updatedAt;
    private double availableCountF;

    public StockItem(
            String storeId,
            String name,
            String purchasePrice,
            String sellingPrice,
            String mrp,
            String priceUnit,
            int availableCount,
            String createdAt,
            String updatedAt,
            double availableCountF
    ) {
        this.storeId = storeId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.priceUnit = priceUnit;
        this.availableCount = availableCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.availableCountF = availableCountF;
    }

    public StockItem() {
    }
}

