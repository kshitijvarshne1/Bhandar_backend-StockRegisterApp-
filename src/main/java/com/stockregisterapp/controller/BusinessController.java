/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 11:56 AM
 *   File: BusinessController.java
 */

package com.stockregisterapp.controller;


import com.stockregisterapp.dao.BusinessDao;
import com.stockregisterapp.model.BusinessModel;
import com.stockregisterapp.repository.BusinessRepository;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class BusinessController {

    @Autowired
    BusinessDao businessDao;

    @Autowired
    BusinessRepository businessRepository;

    @GetMapping("/")
    public String checkApp() {
        return "Working Now";
    }

    @PostMapping("/api/v1/business/create")
    public ResponseModelBoolean createBusiness(@RequestBody BusinessModel businessModel) throws ParseException {
        boolean k = businessDao.createBusiness(businessModel);
        return new ResponseModelBoolean(k);
    }

    @GetMapping("/api/v1/business/getBusinessInfo/{businessId}")
    public BusinessModel getBusinessInfo(@PathVariable String businessId) {
        return businessDao.getBusinessInfo(businessId);
    }


}

