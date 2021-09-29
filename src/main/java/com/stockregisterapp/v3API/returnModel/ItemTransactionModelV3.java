/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:53 PM
 *   File: ItemTransactionModel.java
 */

package com.stockregisterapp.v3API.returnModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTransactionModelV3 {
    private String itemName;
    private String itemId;
    private String openingStock;
    private String closingStock;
    private String priceUnit;
    private String currentStockCount;
    private String lastUpdatedTime;


}

