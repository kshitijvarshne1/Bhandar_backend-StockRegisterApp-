/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 06-Sep-21
 *   Time: 1:27 PM
 *   File: PartyReport.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartyReport {
    private String totalStockInValue;
    private String totalStockOutValue;
    private List<PartyTransactionReport> transactionReportList;

    public PartyReport(String totalStockInValue, String totalStockOutValue, List<PartyTransactionReport> transactionReportList) {
        this.totalStockInValue = totalStockInValue;
        this.totalStockOutValue = totalStockOutValue;
        this.transactionReportList = transactionReportList;
    }
}

