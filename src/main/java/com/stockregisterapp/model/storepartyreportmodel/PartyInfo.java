/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 13-Jul-21
 *   Time: 6:14 PM
 *   File: PartyReport.java
 */

package com.stockregisterapp.model.storepartyreportmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyInfo {
    private String customerName;
    private String createdAt;

    public PartyInfo(String customerName, String createdAt) {
        this.customerName = customerName;
        this.createdAt = createdAt;
    }
}

