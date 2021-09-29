/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:03 PM
 *   File: StockItemNP.java
 */

package com.stockregisterapp.v3API.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class StockItemV3 {
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
    private String itemUnit;
    private double availableCount;
    private String createdAt;
    private String updatedAt;

    public StockItemV3(String storeId, String name, String itemUnit, double availableCount, String createdAt, String updatedAt) {
        this.storeId = storeId;
        this.name = name;
        this.itemUnit = itemUnit;
        this.availableCount = availableCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public StockItemV3() {

    }
}

