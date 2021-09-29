/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:32 PM
 *   File: StockItemControllerV2.java
 */

package com.stockregisterapp.v2API.controller;


import com.stockregisterapp.v2API.dao.StockItemDaoV2;
import com.stockregisterapp.v2API.model.StockItemFullReportV2;
import com.stockregisterapp.v2API.model.StockItemReportModelV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockItemControllerV2 {
    @Autowired
    StockItemDaoV2 stockItemDaoV2;

    @GetMapping("/api/v2/stockItem/getFullReportStockItem/{storeId}")
    public StockItemFullReportV2 getFullReportStockItem(@PathVariable String storeId) {
        return stockItemDaoV2.getFullReportStockItem(storeId);
    }
}

