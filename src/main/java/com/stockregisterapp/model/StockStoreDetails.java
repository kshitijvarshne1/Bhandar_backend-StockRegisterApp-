/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 19-Jul-21
 *   Time: 5:21 PM
 *   File: StockStoreDetails.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockStoreDetails {
    private String storeId;
    private String availableCount;
    private String stockAddDate;

    public StockStoreDetails(String storeId, String availableCount, String stockAddDate) {
        this.storeId = storeId;
        this.availableCount = availableCount;
        this.stockAddDate = stockAddDate;
    }
}

