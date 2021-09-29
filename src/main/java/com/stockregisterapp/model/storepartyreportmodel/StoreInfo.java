/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 6:13 PM
 *   File: StoreReport.java
 */

package com.stockregisterapp.model.storepartyreportmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StoreInfo {
    private String storeName;
    private String storeId;
    private int stockedItems;

    public StoreInfo(String storeName, String storeId, int stockedItems) {
        this.storeName = storeName;
        this.storeId = storeId;
        this.stockedItems = stockedItems;
    }
}

