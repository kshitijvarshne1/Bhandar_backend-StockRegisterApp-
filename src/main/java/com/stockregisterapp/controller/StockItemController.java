/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 10:31 PM
 *   File: StockItemController.java
 */

package com.stockregisterapp.controller;


import com.stockregisterapp.dao.StockItemDao;
import com.stockregisterapp.model.*;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class StockItemController {
    @Autowired
    StockItemDao stockItemDao;

    @PostMapping("/api/v1/stockItem/createStockItem")
    public ResponseList<String> createStockItem(@RequestBody StockItemModelRequest stockItemModelRequest) throws ParseException {
        List<String> l = stockItemDao.createStockItem(stockItemModelRequest);
        return new ResponseList<>(l);
    }

    @PostMapping("/api/v2/stockItem/createMultipleStockItem")
    public ResponseList<String> createMultipleStockItem(@RequestBody MultipleStockItemModelRequest multipleStockItemModelRequest) {
        return stockItemDao.createMultipleStockItem(multipleStockItemModelRequest);
    }

    @GetMapping("/api/v1/stockItem/getStockItem/{store_id}")
    public ResponseList<StockItemReportModel> getStockItem(@PathVariable String store_id) {
        return stockItemDao.getStockItem(store_id);
    }


    @PutMapping("/api/v1/stockItem/updateStockItem")
    public ResponseModelBoolean updateStockItem(@RequestBody StockItemModelUpdateRequest stockItemModelUpdateRequest) throws ParseException {
        return stockItemDao.updateStockItem(stockItemModelUpdateRequest);
    }


    @GetMapping("/api/v1/stockItem/getFullReportStockItem/{storeId}")
    public StockItemFullReport getFullReportStockItem(@PathVariable String storeId) {
        return stockItemDao.getFullReportStockItem(storeId);

    }

}

