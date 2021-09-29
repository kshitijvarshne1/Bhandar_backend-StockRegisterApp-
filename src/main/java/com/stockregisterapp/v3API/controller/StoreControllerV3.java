/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 2:18 PM
 *   File: StoreController.java
 */

package com.stockregisterapp.v3API.controller;

import com.stockregisterapp.model.StoreReportModel;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.v3API.dao.StoreDaoV3;
import com.stockregisterapp.v3API.model.StoreModelRequestV3;
import com.stockregisterapp.v3API.returnModel.StoreModelV3;
import com.stockregisterapp.v3API.returnModel.StoreReportModelV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class StoreControllerV3 {
    @Autowired
    StoreDaoV3 storeDaoV3;

    @PostMapping("/api/v3/store/createStore")
    public ResponseModel createStore(StoreModelRequestV3 storeModelRequestV3) {
        return storeDaoV3.createStore(storeModelRequestV3);
    }

    @GetMapping("/api/v3/store/getReport/{storeId}/{filterDuration}/{filterStartValue}")
    public StoreReportModelV3 getReport(@PathVariable String storeId, @PathVariable String filterDuration, @PathVariable String filterStartValue) throws ParseException {
        return storeDaoV3.getReport(storeId, filterDuration, filterStartValue);
    }
}

