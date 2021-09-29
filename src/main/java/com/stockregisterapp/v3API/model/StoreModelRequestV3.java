/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:12 PM
 *   File: StoreModelRequestV3.java
 */

package com.stockregisterapp.v3API.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreModelRequestV3 {
    private String name;
    private String businessId;
    private String storeType;
    private String stockEntryType;

    public StoreModelRequestV3(String name, String businessId, String storeType, String stockEntryType) {
        this.name = name;
        this.businessId = businessId;
        this.storeType = storeType;
        this.stockEntryType = stockEntryType;
    }
}

