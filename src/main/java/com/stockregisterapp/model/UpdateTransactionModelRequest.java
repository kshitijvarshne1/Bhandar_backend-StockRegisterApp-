/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 04-Aug-21
 *   Time: 6:41 PM
 *   File: UpdateTransaction.java
 */

package com.stockregisterapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransactionModelRequest {
    private String stockItemId;
    private String priceType;
    private String  price;

    public UpdateTransactionModelRequest(String stockItemId, String priceType, String price) {
        this.stockItemId= stockItemId;
        this.priceType = priceType;
        this.price = price;
    }
}

