/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:44 AM
 *   File: StockTransactionController.java
 */

package com.stockregisterapp.v2API.controller;


import com.stockregisterapp.dao.StockTransactionDao;
import com.stockregisterapp.model.StockTransactionModelRequest;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.returnModel.ResponseReport;
import com.stockregisterapp.v2API.dao.StockTransactionDaoV2;
import com.stockregisterapp.v2API.model.ItemTransactionReportV2;
import com.stockregisterapp.v2API.model.StockTransactionModelRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class StockTransactionControllerV2 {
    @Autowired
    StockTransactionDaoV2 stockTransactionDaoV2;


    @GetMapping("/api/v2/stockTransaction/reportTransaction/{stockItemId}/{filterDuration}/{filterStartValue}")
    public ResponseReport<ItemTransactionReportV2> reportTransaction(@PathVariable String stockItemId, @PathVariable String filterDuration, @PathVariable String filterStartValue) {
        return stockTransactionDaoV2.reportTransaction2(stockItemId, filterDuration, filterStartValue);
    }

    @PostMapping("/api/v2/stockTransaction/createTransaction")
    public ResponseModel createTransaction(@RequestBody StockTransactionModelRequestV2 stockTransactionModelRequest) throws ParseException {
        String res = stockTransactionDaoV2.createTransaction2(stockTransactionModelRequest);
        return new ResponseModel(res);
    }
}

