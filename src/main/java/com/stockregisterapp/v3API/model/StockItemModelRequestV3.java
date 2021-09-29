/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:24 PM
 *   File: StockItemModelRequestV3.java
 */

package com.stockregisterapp.v3API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemModelRequestV3 {
    private String storeId;
    private String name;
    private String itemUnit;
    private double availableCount;

    public StockItemModelRequestV3(String storeId, String name, String itemUnit, double availableCount) {
        this.storeId = storeId;
        this.name = name;
        this.itemUnit = itemUnit;
        this.availableCount = availableCount;
    }
}

