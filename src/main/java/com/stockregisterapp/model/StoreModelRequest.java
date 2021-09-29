/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 9:58 PM
 *   File: StoreModelRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreModelRequest {
    private String name;
    private String businessId;

    public StoreModelRequest(String name, String businessId) {
        this.name = name;
        this.businessId = businessId;
    }
}

