/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 9:54 PM
 *   File: StoreController.java
 */

package com.stockregisterapp.controller;


import com.stockregisterapp.dao.StoreDao;
import com.stockregisterapp.model.StoreModel;
import com.stockregisterapp.model.StoreModelRequest;
import com.stockregisterapp.model.StoreReportModel;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class StoreController {
    @Autowired
    StoreDao storeDao;

    @PostMapping("/api/v1/store/createStore")
    public ResponseModel createStore(@RequestBody StoreModelRequest storeModelRequest) throws ParseException {
        String uuid = storeDao.createStore(storeModelRequest);
        return new ResponseModel(uuid);
    }

    @PutMapping("/api/v1/store/updateStoreName/{storeId}/{storeName}")
    public ResponseModelBoolean updateStoreName(@PathVariable String storeId, @PathVariable String storeName) {
        boolean res = storeDao.updateStoreName(storeId, storeName);
        return new ResponseModelBoolean(res);
    }

    @GetMapping("/api/v1/store/getStore/{business_id}")
    public ResponseList<StoreModel> getStore(@PathVariable String business_id) {
        List<StoreModel> l = storeDao.getStore(business_id);
        return new ResponseList<>(l);
    }

    @GetMapping("/api/v1/store/getReport/{storeId}/{filterDuration}/{filterStartValue}")
    public StoreReportModel getReport(@PathVariable String storeId, @PathVariable String filterDuration, @PathVariable String filterStartValue) throws ParseException {
        return storeDao.getReport2(storeId, filterDuration, filterStartValue);
    }


}

