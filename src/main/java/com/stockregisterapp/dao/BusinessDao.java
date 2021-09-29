/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 06-Jul-21
 *   Time: 5:30 PM
 *   File: BusinessDao.java
 */

package com.stockregisterapp.dao;


import com.stockregisterapp.entity.Business;
import com.stockregisterapp.model.BusinessModel;
import com.stockregisterapp.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class BusinessDao {
    @Autowired
    BusinessRepository businessRepository;

    public Boolean createBusiness(BusinessModel businessModel) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT =dateFormat.format(date);
        Business newBusiness = new Business(businessModel.getBusinessId(), businessModel.getBusinessName(),businessModel.getFullName(), businessModel.getMobile()
                , businessModel.getCurrencyCode(), todayDT, todayDT);
        try {
            businessRepository.save(newBusiness);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public BusinessModel getBusinessInfo(String businessId) {
        Optional<Business> b= businessRepository.findById(businessId);
        BusinessModel bm = new BusinessModel(b.get().getBusinessId(),b.get().getBusinessName(),b.get().getFullName(),b.get().getMobile(),b.get().getCurrencyCode());
        if(bm==null){
            return null;
        }
        return bm;
    }
}

