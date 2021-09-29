/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Aug-21
 *   Time: 6:54 PM
 *   File: MultipleStockItemModelRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleStockItemModelRequest {
    private String storeId;
    private List<StockItemRequest> stockItemsList;

    public MultipleStockItemModelRequest(String storeId, List<StockItemRequest> stockItemsList) {
        this.storeId = storeId;
        this.stockItemsList = stockItemsList;
    }
}

