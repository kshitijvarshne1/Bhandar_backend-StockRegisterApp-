/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 1:33 PM
 *   File: PartyController.java
 */

package com.stockregisterapp.controller;


import com.stockregisterapp.dao.PartyDao;
import com.stockregisterapp.entity.Party;
import com.stockregisterapp.model.PartyModel;
import com.stockregisterapp.model.PartyModelRequest;
import com.stockregisterapp.model.PartyReport;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import com.stockregisterapp.returnModel.ResponseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class PartyController {

    @Autowired
    PartyDao partyDao;

    @PostMapping("/api/v1/party/createParty")
    public ResponseModel createParty(@RequestBody PartyModelRequest partyModelRequest) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        Party party = new Party(
                partyModelRequest.getName(),
                partyModelRequest.getMobile(),
                partyModelRequest.getBusinessId(),
                todayDT,
                todayDT,
                false
        );
        String uuid = partyDao.createParty(party);
        return new ResponseModel(uuid);
    }

    @GetMapping("/api/v1/party/getParty/{businessId}")
    public ResponseList<PartyModel> getParty(@PathVariable String businessId) {
        return partyDao.getParty(businessId);
    }

    @PutMapping("/api/v1/party/updatePartyInfo/{partyId}/{partyName}/{partyPhone}")
    public ResponseModelBoolean updatePartyInfo(@PathVariable String partyId, @PathVariable String partyName, @PathVariable String partyPhone) {
        return partyDao.updatePartyInfo(partyId, partyName, partyPhone);

    }
    @DeleteMapping("/api/v2/party/deleteParty/{partyId}")
    public ResponseModelBoolean deleteParty(@PathVariable String partyId){
        return partyDao.deleteParty(partyId);
    }
    @GetMapping("/api/v2/party/reportPartyTransactions/{storeId}/{partyId}")
    public ResponseReport<PartyReport> reportPartyTransactions(@PathVariable String storeId, @PathVariable String partyId){
        return partyDao.reportPartyTransactions(storeId, partyId);
    }
}

