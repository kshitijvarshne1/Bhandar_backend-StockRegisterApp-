/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 9:56 PM
 *   File: PartyModelRequest.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PartyModel {
    private String partyId;
    private String name;
    private String mobile;
    private String business_id;

    public PartyModel(String partyId, String name, String mobile, String business_id) {
        this.partyId = partyId;
        this.name = name;
        this.mobile = mobile;
        this.business_id = business_id;
    }
}

