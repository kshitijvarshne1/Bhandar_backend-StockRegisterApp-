/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 06-Jul-21
 *   Time: 5:28 PM
 *   File: PartyModelRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyModelRequest {
    private String name;
    private String mobile;
    private String businessId;

    public PartyModelRequest(String name, String mobile, String businessId) {
        this.name = name;
        this.mobile = mobile;
        this.businessId = businessId;
    }

}

