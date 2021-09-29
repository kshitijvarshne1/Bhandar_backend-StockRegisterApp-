/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 2:22 PM
 *   File: StockTransactionController.java
 */

package com.stockregisterapp.v3API.dao;

import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.v3API.model.StockTransactionModelRequestV3;
import com.stockregisterapp.v3API.repository.StockTransactionRepositoryV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class StockTransactionDaoV3 {
    @Autowired
    StockTransactionRepositoryV3 stockTransactionRepositoryV3;


    public ResponseModel createTransaction(StockTransactionModelRequestV3 stockTransactionModelRequestV3) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);

        return new ResponseModel(null);
    }
}

