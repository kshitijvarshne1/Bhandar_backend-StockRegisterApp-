/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 11:07 AM
 *   File: Business.java
 */

package com.stockregisterapp.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Business {
    @Id
    private String businessId;
    private String businessName;
    private String fullName;
    public String mobile;
    private String currencyCode;
    private String createdAt;
    private String updatedAt;

    public Business(String businessId, String businessName, String fullName, String mobile, String currencyCode, String createdAt, String updatedAt) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.fullName = fullName;
        this.mobile = mobile;
        this.currencyCode = currencyCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Business() {
    }
}

