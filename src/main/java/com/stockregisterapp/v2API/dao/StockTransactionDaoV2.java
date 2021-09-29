/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 31-Aug-21
 *   Time: 11:46 AM
 *   File: StockTransactionDaoV2.java
 */

package com.stockregisterapp.v2API.dao;

import com.stockregisterapp.dao.StoreDao;
import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.entity.StockTransaction;
import com.stockregisterapp.repository.StockItemRepository;
import com.stockregisterapp.repository.StockTransactionRepository;
import com.stockregisterapp.returnModel.ResponseReport;
import com.stockregisterapp.v2API.model.ItemTransactionReportV2;
import com.stockregisterapp.v2API.model.StockTransactionModelRequestV2;
import com.stockregisterapp.v2API.model.StockTransactionModelV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockTransactionDaoV2 {
    @Autowired
    StockTransactionRepository stockTransactionRepository;
    @Autowired
    StockItemRepository stockItemRepository;
    @Autowired
    StoreDao storeDao;

    public String createTransaction2(StockTransactionModelRequestV2 stockTransactionModelRequest) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        StockItem stockItem = new StockItem();
        StockTransaction s;
        if (stockTransactionModelRequest.getStockTxnEntity().equalsIgnoreCase("STORE")) {
            stockItem = stockItemRepository.getById(stockTransactionModelRequest.getStockItemId());
            s = new StockTransaction(
                    todayDT,
                    stockTransactionModelRequest.getStockItemId(),
                    stockTransactionModelRequest.getTxnType(),
                    stockTransactionModelRequest.getPreTxnStockCount(),
                    stockTransactionModelRequest.getPostTxnStockCount(),
                    Integer.parseInt(stockItem.getPurchasePrice()),
                    stockTransactionModelRequest.getStockTxnEntity(),
                    stockTransactionModelRequest.getSourceTxnEntityId(),
                    stockTransactionModelRequest.getSourceEntityName(),
                    Double.parseDouble(stockItem.getPurchasePrice())
            );
            stockTransactionRepository.save(s);
            if (stockTransactionModelRequest.isAutoTransfer() && stockTransactionModelRequest.getTxnType().equals("OUT")) {
                double itemStore2Count = Double.parseDouble(stockTransactionModelRequest.getPreTxnStockCount()) - Double.parseDouble(stockTransactionModelRequest.getPreTxnStockCount());

                List<StockItem> nextStoreItems = stockItemRepository.findByStoreId(stockTransactionModelRequest.getSourceTxnEntityId());

                StockItem nexStoreItem = null;

                for (StockItem item : nextStoreItems) {
                    if (item.getName().equals(stockItem.getName())) {
                        nexStoreItem = item;
                        break;
                    }
                }

                if (nexStoreItem == null) {
                    StockItem newStockItem = new StockItem(
                            stockTransactionModelRequest.getSourceTxnEntityId(),
                            stockItem.getName(),
                            stockItem.getPurchasePrice(),
                            stockItem.getSellingPrice(),
                            stockItem.getMrp(),
                            stockItem.getPriceUnit(),
                            (int) itemStore2Count,
                            todayDT,
                            todayDT,
                            itemStore2Count
                    );

                    nexStoreItem = stockItemRepository.save(newStockItem);
                }

                StockTransaction s2 = new StockTransaction(
                        todayDT,
                        nexStoreItem.getStockItemId(),
                        stockTransactionModelRequest.getTxnType(),
                        "0",
                        String.valueOf(itemStore2Count),
                        Integer.parseInt(nexStoreItem.getPurchasePrice()),
                        stockTransactionModelRequest.getStockTxnEntity(),
                        stockItem.getStoreId(),
                        stockTransactionModelRequest.getSourceEntityName(),
                        Double.parseDouble(nexStoreItem.getPurchasePrice())
                );
                stockTransactionRepository.save(s2);
            }
        } else {
            s = new StockTransaction(
                    todayDT,
                    stockTransactionModelRequest.getStockItemId(),
                    stockTransactionModelRequest.getTxnType(),
                    stockTransactionModelRequest.getPreTxnStockCount(),
                    stockTransactionModelRequest.getPostTxnStockCount(),
                    stockTransactionModelRequest.getTxnUnitPrice().intValue(),
                    stockTransactionModelRequest.getStockTxnEntity(),
                    stockTransactionModelRequest.getSourceTxnEntityId(),
                    stockTransactionModelRequest.getSourceEntityName(),
                    stockTransactionModelRequest.getTxnUnitPrice()
            );
            stockTransactionRepository.save(s);
        }

        Optional<StockItem> st = stockItemRepository.findById(s.getStockItemId());
        st.get().setAvailableCount((int) Double.parseDouble(s.getPostTxnStockCount()));
        st.get().setAvailableCountF(Double.parseDouble(s.getPostTxnStockCount()));
        stockItemRepository.updateAvailableCount(st.get().getStockItemId(), st.get().getAvailableCount());
        stockItemRepository.updateAvailableCountF(st.get().getStockItemId(), st.get().getAvailableCountF());
        return s.getTxnId();
    }


    public ResponseReport<ItemTransactionReportV2> reportTransaction2(String stockItemId, String filterDuration, String filterStartValue) {
        filterStartValue = filterStartValue.replaceAll("-", "/");
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findByStockItemId(stockItemId);
        List<StockTransaction> filterdTransaction = storeDao.filterTransaction(
                stockTransactionList,
                filterDuration,
                filterStartValue
        );


        Collections.sort(filterdTransaction, new Comparator<StockTransaction>() {
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            public int compare(StockTransaction o1, StockTransaction o2) {
                try {
                    return -formatter.parse(o1.getTransactionTs()).compareTo(formatter.parse(o2.getTransactionTs()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        ItemTransactionReportV2 result = getItemTransactionReport(filterdTransaction);
        StockTransaction stockTransaction = storeDao.getOneLastTransaction(stockTransactionList, filterStartValue);
        if (filterdTransaction.size() == 0) {
            if (stockTransaction == null) {
                result.setOpeningStock("-");
                result.setClosingStock("-");
            } else {
                result.setOpeningStock(stockTransaction.getPostTxnStockCount());
                result.setClosingStock(stockTransaction.getPostTxnStockCount());
            }
        }

        if (filterdTransaction.size() > 0 && stockTransaction == null) {
            result.setOpeningStock("-");
        }

        if (filterdTransaction.size() == 1 && stockTransaction == null) {
            result.setClosingStock(stockTransactionList.get(0).getPostTxnStockCount());
        }

        return new ResponseReport<>(result);
    }

    private ItemTransactionReportV2 getItemTransactionReport(List<StockTransaction> filterdTransaction) {
        ItemTransactionReportV2 obj = new ItemTransactionReportV2();
        float openingStock = 0;
        float closingStock = 0;

        float amountTotalStockIn = 0;
        float amountTotalStockOut = 0;
        float inCount = 0;
        float outCount = 0;

        List<StockTransactionModelV2> stockTransactionModels = getStockTransaction(filterdTransaction);
        int i = 0;
        for (StockTransactionModelV2 stm : stockTransactionModels) {
            switch (stm.getTxnType()) {
                case "IN":
                    inCount += Float.parseFloat(stm.getStockCount());
                    amountTotalStockIn += Float.parseFloat(stm.getTotalAmount());
                    break;
                case "OUT":
                    outCount += Float.parseFloat(stm.getStockCount());
                    amountTotalStockOut += Float.parseFloat(stm.getTotalAmount());
                    break;
                default:
                    break;
            }
            if (i == 0) {
                closingStock = Float.parseFloat(stm.getAvailableStock());
            }
            if (i == stockTransactionModels.size() - 1) {
                openingStock = Float.parseFloat(stm.getAvailableStock());
                switch (stm.getTxnType()) {
                    case "IN":
                        openingStock -= Float.parseFloat(stm.getStockCount());
                        break;
                    case "OUT":
                        openingStock += Float.parseFloat(stm.getStockCount());
                        break;
                    default:
                        break;
                }

            }
            i++;
        }
        obj.setTotalStockIn(String.valueOf(inCount));
        obj.setTotalStockOut(String.valueOf(outCount));
        obj.setAmountTotalStockIn(String.valueOf(amountTotalStockIn));
        obj.setAmountTotalStockOut(String.valueOf(amountTotalStockOut));
        obj.setOpeningStock(String.valueOf(openingStock));
        obj.setClosingStock(String.valueOf(closingStock));
        obj.setTransactions(stockTransactionModels);
        return obj;
    }

    private List<StockTransactionModelV2> getStockTransaction(List<StockTransaction> filterdTransaction) {
        List<StockTransactionModelV2> result = new ArrayList<>();
        for (StockTransaction st : filterdTransaction) {
            String pk = st.getTransactionTs().substring(0, 16);
            if (Integer.parseInt(st.getTransactionTs().substring(11, 13)) >= 0 && Integer.parseInt(st.getTransactionTs().substring(11, 13)) <= 11) {
                pk = pk + " AM";
            } else {
                int hr = Integer.parseInt(pk.substring(11, 13)) - 12;
                if (hr == 0) {
                    hr = 12;
                } else if (hr == 12) {
                    hr = 0;
                }
                String newTime = pk.substring(0, 10) + " " + hr + pk.substring(13, 16);
                pk = newTime + " PM";
            }
            pk = setMonth(pk);
            float stockCount = 0;
            switch (st.getTxnType()) {
                case "IN":
                    stockCount = Math.max(0, Float.parseFloat(st.getPostTxnStockCount()) - Float.parseFloat(st.getPreTxnStockCount()));
                    break;
                case "OUT":
                    stockCount = Math.max(0, Float.parseFloat(st.getPreTxnStockCount()) - Float.parseFloat(st.getPostTxnStockCount()));
                    break;
                default:
                    break;
            }
            if (st.getSourceTxnEntityId().equalsIgnoreCase("NULL") && st.getStockTxnEntity().equalsIgnoreCase("STORE")) {
                continue;
            }
            result.add(new StockTransactionModelV2(
                    st.getTxnId(),
                    pk,
                    st.getTxnType(),
                    String.valueOf(stockCount),
                    String.valueOf(st.getTxnUnitPriceF()),
                    String.valueOf(stockCount * st.getTxnUnitPriceF()),
                    st.getPostTxnStockCount(),
                    st.getStockTxnEntity(),
                    st.getSourceEntityName(),
                    st.getPreTxnStockCount(),
                    st.getPostTxnStockCount(),
                    st.getStockItemId()
            ));
        }
        return result;
    }

    private String setMonth(String pk) {
        String[] months = {"Jan", "Feb", "Mar", "Api", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String monthsStr = pk.substring(3, 5);
        String MM = months[Integer.parseInt(monthsStr) - 1];
        String newStr = pk.substring(0, 2) + " " + MM + ", " + pk.substring(6);
        return newStr;
    }
}

