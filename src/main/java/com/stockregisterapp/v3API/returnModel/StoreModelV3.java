/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 7:12 PM
 *   File: StoreModelV3.java
 */

package com.stockregisterapp.v3API.returnModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreModelV3 {
    private String storeId;
    private String name;
    private String businessId;

    public StoreModelV3(String storeId, String name, String businessId) {
        this.storeId = storeId;
        this.name = name;
        this.businessId = businessId;
    }
}

