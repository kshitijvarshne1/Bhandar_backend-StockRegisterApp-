/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:50 PM
 *   File: StoreModelV3.java
 */

package com.stockregisterapp.v3API.returnModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreReportModelV3 {
    private List<ItemTransactionModelV3> itemTransactionModelV3List;

    public StoreReportModelV3(List<ItemTransactionModelV3> itemTransactionModelV3List) {
        this.itemTransactionModelV3List = itemTransactionModelV3List;
    }
}

