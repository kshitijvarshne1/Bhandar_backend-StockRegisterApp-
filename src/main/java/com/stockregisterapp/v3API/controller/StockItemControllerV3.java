/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 2:19 PM
 *   File: StockItemController.java
 */

package com.stockregisterapp.v3API.controller;

import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.v3API.dao.StockItemDaoV3;
import com.stockregisterapp.v3API.model.StockItemModelRequestV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockItemControllerV3 {
    @Autowired
    StockItemDaoV3 stockItemDaoV3;

    @PostMapping("/api/v3/stockItem/createStockItem")
    private ResponseModel createStockItem(StockItemModelRequestV3 stockItemModelRequestV3){
        return stockItemDaoV3.createStockItem(stockItemModelRequestV3);
    }
}

