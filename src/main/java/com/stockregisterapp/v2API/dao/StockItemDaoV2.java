/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:33 PM
 *   File: StockItemDaoV2.java
 */

package com.stockregisterapp.v2API.dao;


import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.repository.StockItemRepository;
import com.stockregisterapp.repository.StockTransactionRepository;
import com.stockregisterapp.v2API.model.StockItemFullReportV2;
import com.stockregisterapp.v2API.model.StockItemReportModelV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockItemDaoV2 {
    @Autowired
    StockItemRepository stockItemRepository;

    @Autowired
    StockTransactionRepository stockTransactionRepository;

    public StockItemFullReportV2 getFullReportStockItem(String storeId) {
        List<StockItem> l = stockItemRepository.findByStoreId(storeId);
        List<StockItemReportModelV2> res = new ArrayList<>();
        String totalStockValue = "";
        for (StockItem s : l) {
            String time = s.getUpdatedAt();
            String updatedTime = time.substring(11, 16);
            String hr = time.substring(11, 13);
            String mm = time.substring(14, 16);
            if (Integer.parseInt(hr) >= 12 && Integer.parseInt(hr) <= 23) {
                updatedTime = updatedTime + " PM";
            } else {
                updatedTime = updatedTime + " AM";
            }
            if (totalStockValue.length() == 0) {
                totalStockValue = String.valueOf(Double.parseDouble(s.getSellingPrice()) * s.getAvailableCountF());
            } else {
                totalStockValue = String.valueOf(Double.parseDouble(totalStockValue) + Double.parseDouble(s.getSellingPrice()) * s.getAvailableCountF());
            }

            Double mrpChecked = s.getMrp().isEmpty() ? 0.0 : Double.parseDouble(s.getMrp());

            res.add(new StockItemReportModelV2(
                    s.getStockItemId(),
                    s.getStoreId(),
                    s.getName(),
                    Double.parseDouble(s.getPurchasePrice()),
                    Double.parseDouble(s.getSellingPrice()),
                    mrpChecked,
                    s.getPriceUnit(),
                    s.getAvailableCountF(),
                    updatedTime
                    )
            );
        }
        StockItemFullReportV2 result = new StockItemFullReportV2();
        result.setTotalStockValue(totalStockValue);
        result.setStockItemReportModels(res);
        return result;

    }

}

