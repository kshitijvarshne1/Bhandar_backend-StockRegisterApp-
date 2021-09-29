/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 2:41 PM
 *   File: StorePartyController.java
 */

package com.stockregisterapp.controller;


import com.stockregisterapp.dao.StoreDao;
import com.stockregisterapp.model.StorePartyReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorePartyController {
    @Autowired
    StoreDao storeDao;

    @GetMapping("/api/v1/storyParty/reportStoreParty/{businessId}")
    public StorePartyReportModel reportStoreParty(@PathVariable String businessId){
        return storeDao.reportStoreParty(businessId);
    }
}

