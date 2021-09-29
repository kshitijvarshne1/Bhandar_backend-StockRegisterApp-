/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 09-Jul-21
 *   Time: 12:53 AM
 *   File: StockTransactionController.java
 */

package com.stockregisterapp.controller;


import com.stockregisterapp.dao.StockTransactionDao;
import com.stockregisterapp.model.ItemTransactionReport;
import com.stockregisterapp.model.StockTransactionModelRequest;
import com.stockregisterapp.model.UpdateTransactionRequest;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import com.stockregisterapp.returnModel.ResponseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class StockTransactionController {
    @Autowired
    StockTransactionDao stockTransactionDao;

    @PostMapping("/api/v1/stockTransaction/createTransaction")
    public ResponseModel createTransaction(@RequestBody StockTransactionModelRequest stockTransactionModelRequest) throws ParseException {
        String res = stockTransactionDao.createTransaction(stockTransactionModelRequest);
        return new ResponseModel(res);
    }


    @GetMapping("/api/v1/stockTransaction/reportTransaction/{stockItemId}/{filterDuration}/{filterStartValue}")
    public ResponseReport<ItemTransactionReport> reportTransaction(@PathVariable String stockItemId, @PathVariable String filterDuration, @PathVariable String filterStartValue) {
        return stockTransactionDao.reportTransaction2(stockItemId, filterDuration, filterStartValue);
    }


    @DeleteMapping("/api/v2/deleteTransaction/{stockItemId}/{txnId}")
    public ResponseModelBoolean deleteTransaction(@PathVariable String stockItemId, @PathVariable String txnId) {
        return stockTransactionDao.deleteTransaction(stockItemId, txnId);

    }

    @PutMapping("/api/v2/updateTransaction/")
    public ResponseModelBoolean updateTransaction(@RequestBody UpdateTransactionRequest updateTransactionRequest) {
        return stockTransactionDao.updateTransaction(updateTransactionRequest);

    }

}

