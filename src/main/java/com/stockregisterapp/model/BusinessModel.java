/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 12:43 PM
 *   File: BusinessModel.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessModel {
    private String businessId;
    private String businessName;
    private String fullName;
    private String mobile;
    private String currencyCode;

    public BusinessModel(String businessId, String businessName, String fullName, String mobile, String currencyCode) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.fullName = fullName;
        this.mobile = mobile;
        this.currencyCode = currencyCode;
    }
}

