/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Aug-21
 *   Time: 2:40 PM
 *   File: StoreControllerV2.java
 */

package com.stockregisterapp.v2API.controller;



import com.stockregisterapp.v2API.dao.StoreDaoV2;
import com.stockregisterapp.v2API.model.StoreReportModelV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class StoreControllerV2 {
    @Autowired
    StoreDaoV2 storeDaoV2;

    @GetMapping("/api/v2/store/getReport/{storeId}/{filterDuration}/{filterStartValue}")
    public StoreReportModelV2 getReportV2(@PathVariable String storeId, @PathVariable String filterDuration, @PathVariable String filterStartValue) throws ParseException {
        return storeDaoV2.getReport2(storeId, filterDuration, filterStartValue);
    }
}

