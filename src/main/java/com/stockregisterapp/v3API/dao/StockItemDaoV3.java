/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 2:20 PM
 *   File: StockItemDao.java
 */

package com.stockregisterapp.v3API.dao;

import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.v3API.entity.StockItemV3;
import com.stockregisterapp.v3API.entity.StockTransactionV3;
import com.stockregisterapp.v3API.model.StockItemModelRequestV3;
import com.stockregisterapp.v3API.repository.StockItemRepositoryV3;
import com.stockregisterapp.v3API.repository.StockTransactionRepositoryV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class StockItemDaoV3 {
    @Autowired
    StockItemRepositoryV3 stockItemRepositoryV3;

    @Autowired
    StockTransactionRepositoryV3 stockTransactionRepositoryV3;

    public ResponseModel createStockItem(StockItemModelRequestV3 stockItemModelRequestV3) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        StockItemV3 stockItemV3 = new StockItemV3(stockItemModelRequestV3.getStoreId(), stockItemModelRequestV3.getName(), stockItemModelRequestV3.getItemUnit(), stockItemModelRequestV3.getAvailableCount(), todayDT, todayDT);
        stockItemRepositoryV3.save(stockItemV3);

        StockTransactionV3 stockTransactionV3 = new StockTransactionV3(todayDT, stockItemV3.getStockItemId(), "IN", 0.0, stockItemV3.getAvailableCount(), "STORE", "NULL");
        stockTransactionRepositoryV3.save(stockTransactionV3);
        return new ResponseModel(stockItemV3.getStockItemId());
    }

    public List<StockItemV3> getAllStockItemsV3(String storeId) {
        return stockItemRepositoryV3.findByStoreId(storeId);
    }
}

