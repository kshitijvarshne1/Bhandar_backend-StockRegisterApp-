/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 9:59 PM
 *   File: StoreModel.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreModel {
    private String storeId;
    private String name;
    private String businessId;

    public StoreModel(String storeId, String name, String businessId) {
        this.storeId = storeId;
        this.name = name;
        this.businessId = businessId;
    }
}

