/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 2:22 PM
 *   File: StockTransactionController.java
 */

package com.stockregisterapp.v3API.controller;

import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.v3API.dao.StockTransactionDaoV3;
import com.stockregisterapp.v3API.model.StockTransactionModelRequestV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockTransactionControllerV3 {
    @Autowired
    StockTransactionDaoV3 stockTransactionDaoV3;

    @PostMapping("/api/v3/stockTransaction/createTransaction")
    public ResponseModel createTransaction(StockTransactionModelRequestV3 stockTransactionModelRequestV3){
        return stockTransactionDaoV3.createTransaction(stockTransactionModelRequestV3);
    }


}

