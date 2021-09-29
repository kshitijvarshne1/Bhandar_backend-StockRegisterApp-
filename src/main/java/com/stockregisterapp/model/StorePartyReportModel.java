/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 6:15 PM
 *   File: StorePartyReportModel.java
 */

package com.stockregisterapp.model;


import com.stockregisterapp.model.storepartyreportmodel.PartyInfo;
import com.stockregisterapp.model.storepartyreportmodel.StoreInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StorePartyReportModel {
    private List<StoreInfo> storeInfoList;
    private List<PartyInfo> partyInfoList;

    public StorePartyReportModel(List<StoreInfo> storeInfoList, List<PartyInfo> partyInfoList) {
        this.storeInfoList = storeInfoList;
        this.partyInfoList = partyInfoList;
    }
}

