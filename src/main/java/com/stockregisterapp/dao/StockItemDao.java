/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 06-Jul-21
 *   Time: 5:30 PM
 *   File: StockItemDao.java
 */

package com.stockregisterapp.dao;


import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.entity.StockTransaction;
import com.stockregisterapp.model.*;
import com.stockregisterapp.repository.StockItemRepository;
import com.stockregisterapp.repository.StockTransactionRepository;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class StockItemDao {
    @Autowired
    StockItemRepository stockItemRepository;

    @Autowired
    StockTransactionRepository stockTransactionRepository;

    public List<String> createStockItem(StockItemModelRequest stockItemModelRequest) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        List<StockItem> list = new ArrayList<>();
        List<String> res = new ArrayList<>();
        for (StockStoreDetails s : stockItemModelRequest.getStockStoreDetails()) {
            StockItem stockItem = new StockItem(
                    s.getStoreId(),
                    stockItemModelRequest.getName(),
                    stockItemModelRequest.getPurchasePrice(),
                    stockItemModelRequest.getSellingPrice(),
                    stockItemModelRequest.getMrp(),
                    stockItemModelRequest.getPriceUnit(),
                    (int) Double.parseDouble(s.getAvailableCount()),
                    todayDT,
                    todayDT,
                    Double.parseDouble(s.getAvailableCount())
            );
            list.add(stockItem);
        }
        List<StockTransaction> stk = new ArrayList<>();
        List<StockItem> resultData = stockItemRepository.saveAll(list);
        for (StockItem s : resultData) {
            res.add(s.getStockItemId());
            stk.add(
                    new StockTransaction(
                            todayDT,
                            s.getStockItemId(),
                            "IN",
                            String.valueOf(0),
                            String.valueOf(s.getAvailableCountF()),
                            (int) Double.parseDouble(s.getSellingPrice()),
                            "STORE",
                            "NULL",
                            "STORE",
                            Double.parseDouble(s.getSellingPrice())
                    )
            );
        }
        stockTransactionRepository.saveAll(stk);
        return res;
    }

    public ResponseList<String> createMultipleStockItem(MultipleStockItemModelRequest multipleStockItemModelRequest) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        List<StockItem> list = new ArrayList<>();
        List<String> res = new ArrayList<>();
        String storeId = multipleStockItemModelRequest.getStoreId();
        for (StockItemRequest s : multipleStockItemModelRequest.getStockItemsList()) {
            StockItem stockItem = new StockItem(
                    storeId,
                    s.getName(),
                    String.valueOf(s.getPurchasePrice()),
                    String.valueOf(s.getSellingPrice()),
                    String.valueOf(s.getMrp()),
                    s.getPriceUnit(),
                    (int)s.getAvailableCount(),
                    todayDT,
                    todayDT,
                    s.getAvailableCount()
            );
            list.add(stockItem);
        }
        List<StockTransaction> stk = new ArrayList<>();
        List<StockItem> resultData = stockItemRepository.saveAll(list);

        for (StockItem s : resultData) {
            res.add(s.getStockItemId());
            stk.add(
                    new StockTransaction(
                            todayDT,
                            s.getStockItemId(),
                            "IN",
                            String.valueOf(0),
                            String.valueOf(s.getAvailableCountF()),
                            (int)Double.parseDouble(s.getSellingPrice()),
                            "STORE",
                            "NULL",
                            "STORE",
                            Double.parseDouble(s.getSellingPrice())
                    ));
        }
        stockTransactionRepository.saveAll(stk);
        return new ResponseList<>(res);
    }

    public ResponseList<StockItemReportModel> getStockItem(String store_id) {
        List<StockItem> l = stockItemRepository.findByStoreId(store_id);
        List<StockItemReportModel> res = new ArrayList<>();
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
            res.add(
                    new StockItemReportModel(
                            s.getStockItemId(),
                            s.getStoreId(),
                            s.getName(),
                            s.getPurchasePrice(),
                            s.getSellingPrice(),
                            s.getMrp(),
                            s.getPriceUnit(),
                            s.getAvailableCount(),
                            updatedTime
                    )
            );
        }
        return new ResponseList<>(res);
    }

    public StockItemFullReport getFullReportStockItem(String storeId) {
        List<StockItem> l = stockItemRepository.findByStoreId(storeId);
        List<StockItemReportModel> res = new ArrayList<>();
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
                totalStockValue = String.valueOf(Integer.parseInt(s.getSellingPrice()) * s.getAvailableCount());
            } else {
                totalStockValue = String.valueOf(Integer.parseInt(totalStockValue) + Integer.parseInt(s.getSellingPrice()) * s.getAvailableCount());
            }

            res.add(new StockItemReportModel(s.getStockItemId(), s.getStoreId(), s.getName(), s.getPurchasePrice(), s.getSellingPrice(), s.getMrp(), s.getPriceUnit(), s.getAvailableCount(), updatedTime));
        }
        StockItemFullReport result = new StockItemFullReport();
        result.setTotalStockValue(totalStockValue);
        result.setStockItemReportModels(res);
        return result;

    }


    public ResponseModelBoolean updateStockItem(StockItemModelUpdateRequest stockItemModelUpdateRequest) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        try {
            stockItemRepository.updateStockItem(stockItemModelUpdateRequest.getStockItemId(), stockItemModelUpdateRequest.getName(), stockItemModelUpdateRequest.getPurchasePrice(), stockItemModelUpdateRequest.getSellingPrice(), stockItemModelUpdateRequest.getMrp(), stockItemModelUpdateRequest.getPriceUnit(), todayDT);
            return new ResponseModelBoolean(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseModelBoolean(false);
        }
    }

}

