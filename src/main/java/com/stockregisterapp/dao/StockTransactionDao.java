/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 09-Jul-21
 *   Time: 12:54 AM
 *   File: StockTransactionDao.java
 */

package com.stockregisterapp.dao;


import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.entity.StockTransaction;
import com.stockregisterapp.model.ItemTransactionReport;
import com.stockregisterapp.model.StockTransactionModel;
import com.stockregisterapp.model.StockTransactionModelRequest;
import com.stockregisterapp.model.UpdateTransactionRequest;
import com.stockregisterapp.repository.StockItemRepository;
import com.stockregisterapp.repository.StockTransactionRepository;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import com.stockregisterapp.returnModel.ResponseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockTransactionDao {
    @Autowired
    StockTransactionRepository stockTransactionRepository;
    @Autowired
    StockItemRepository stockItemRepository;
    @Autowired
    StoreDao storeDao;

    public String createTransaction(StockTransactionModelRequest stockTransactionModelRequest) throws ParseException {
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

            if (stockTransactionModelRequest.isAutoTransfer() &&
                    stockTransactionModelRequest.getTxnType().equals("OUT")
            ) {
                double itemStore2Count =
                        Double.parseDouble(stockTransactionModelRequest.getPreTxnStockCount()) -
                                Double.parseDouble(stockTransactionModelRequest.getPreTxnStockCount());

                List<StockItem> nextStoreItems =
                        stockItemRepository.findByStoreId(
                                stockTransactionModelRequest.getSourceTxnEntityId()
                        );

                StockItem nexStoreItem = null;

                for(StockItem item: nextStoreItems) {
                    if(item.getName().equals(stockItem.getName()))
                    {
                        nexStoreItem =  item;
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
                    stockTransactionModelRequest.getTxnUnitPrice(),
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

    public ResponseReport<ItemTransactionReport> reportTransaction2(String stockItemId, String filterDuration, String filterStartValue) {
        filterStartValue = filterStartValue.replaceAll("-", "/");
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findByStockItemId(stockItemId);
        List<StockTransaction> filterdTransaction = storeDao.filterTransaction(stockTransactionList, filterDuration, filterStartValue);


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
        ItemTransactionReport result = getItemTransactionReport(filterdTransaction);
        if (filterdTransaction.size() == 0) {
            StockTransaction stockTransaction = storeDao.getOneLastTransaction(stockTransactionList, filterStartValue);
            if (stockTransaction == null) {
                result.setOpeningStock(0);
                result.setClosingStock(0);
            } else {
                result.setOpeningStock(Integer.parseInt(stockTransaction.getPostTxnStockCount()));
                result.setClosingStock(Integer.parseInt(stockTransaction.getPostTxnStockCount()));
            }
        }
        if (stockTransactionList.size() == 1 && stockTransactionList.get(0).getTxnType().equalsIgnoreCase("IN") && stockTransactionList.get(0).getSourceTxnEntityId().equalsIgnoreCase("NULL")) {
            result.setOpeningStock(Integer.parseInt(stockTransactionList.get(0).getPostTxnStockCount()));
            result.setClosingStock(Integer.parseInt(stockTransactionList.get(0).getPostTxnStockCount()));
        }


        return new ResponseReport<>(result);
    }

    private List<StockTransactionModel> getStockTransaction(List<StockTransaction> filterdTransaction) {
        List<StockTransactionModel> result = new ArrayList<>();
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
            int stockCount = 0;
            switch (st.getTxnType()) {
                case "IN":
                    stockCount = Math.max(0, Integer.parseInt(st.getPostTxnStockCount()) - Integer.parseInt(st.getPreTxnStockCount()));
                    break;
                case "OUT":
                    stockCount = Math.max(0, Integer.parseInt(st.getPreTxnStockCount()) - Integer.parseInt(st.getPostTxnStockCount()));
                    break;
                default:
                    break;
            }
            if (st.getSourceTxnEntityId().equalsIgnoreCase("NULL") && st.getStockTxnEntity().equalsIgnoreCase("STORE")) {
                continue;
            }
            result.add(new StockTransactionModel(
                    st.getTxnId(),
                    pk,
                    st.getTxnType(),
                    String.valueOf(stockCount),
                    st.getTxnUnitPrice(),
                    stockCount * st.getTxnUnitPrice(),
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

    private ItemTransactionReport getItemTransactionReport(List<StockTransaction> filterdTransaction) {
        ItemTransactionReport obj = new ItemTransactionReport();
        int openingStock = 0;
        int closingStock = 0;

        int amountTotalStockIn = 0;
        int amountTotalStockOut = 0;
        int inCount = 0;
        int outCount = 0;

        List<StockTransactionModel> stockTransactionModels = getStockTransaction(filterdTransaction);
        int i = 0;
        for (StockTransactionModel stm : stockTransactionModels) {
            switch (stm.getTxnType()) {
                case "IN":
                    inCount += Integer.parseInt(stm.getStockCount());
                    amountTotalStockIn += stm.getTotalAmount();
                    break;
                case "OUT":
                    outCount += Integer.parseInt(stm.getStockCount());
                    amountTotalStockOut += stm.getTotalAmount();
                    break;
                default:
                    break;
            }
            if (i == 0) {
                closingStock = Integer.parseInt(stm.getAvailableStock());
            }
            if (i == stockTransactionModels.size() - 1) {
                openingStock = Integer.parseInt(stm.getAvailableStock());
                switch (stm.getTxnType()) {
                    case "IN":
                        openingStock -= Integer.parseInt(stm.getStockCount());
                        break;
                    case "OUT":
                        openingStock += Integer.parseInt(stm.getStockCount());
                        break;
                    default:
                        break;
                }

            }
            i++;
        }
        obj.setTotalStockIn(String.valueOf(inCount));
        obj.setTotalStockOut(String.valueOf(outCount));
        obj.setAmountTotalStockIn(amountTotalStockIn);
        obj.setAmountTotalStockOut(amountTotalStockOut);
        obj.setOpeningStock(openingStock);
        obj.setClosingStock(closingStock);
        obj.setTransactions(stockTransactionModels);
        return obj;
    }


    public ResponseModelBoolean deleteTransaction(String stockItemId, String txnId) {
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findByStockItemId(stockItemId);
        Collections.sort(stockTransactionList, new Comparator<StockTransaction>() {
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
        Optional<StockItem> itm = stockItemRepository.findById(stockItemId);
        if (stockTransactionList.size() > 1 && stockTransactionList.get(0).getTxnId().equalsIgnoreCase(txnId)) {
            stockItemRepository.updateAvailableCount(
                    stockItemId,
                    Integer.parseInt(stockTransactionList.get(1).getPreTxnStockCount())
            );
            stockItemRepository.updateAvailableCountF(
                    stockItemId,
                    Double.parseDouble(stockTransactionList.get(1).getPreTxnStockCount())
            );
            stockTransactionRepository.deleteById(stockTransactionList.get(0).getTxnId());
            return new ResponseModelBoolean(true);
        }
        return new ResponseModelBoolean(false);
    }

    public ResponseModelBoolean updateTransaction(UpdateTransactionRequest updateTransactionRequest) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findByStockItemId(
                updateTransactionRequest.getStockItemId()
        );
        Collections.sort(stockTransactionList, new Comparator<StockTransaction>() {
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
        if (stockTransactionList.size() > 1 && stockTransactionList.get(0).getTxnId().equalsIgnoreCase(updateTransactionRequest.getTxnId())) {
            String newAvailableCount = updateTransactionRequest.getPostTxnStockCount();
            stockItemRepository.updateAvailableCount(
                    updateTransactionRequest.getStockItemId(),
                    (int) Double.parseDouble(newAvailableCount)
            );
            stockItemRepository.updateAvailableCountF(
                    updateTransactionRequest.getStockItemId(),
                    Double.parseDouble(newAvailableCount)
            );
            stockTransactionRepository.updateTransaction(
                    updateTransactionRequest.getTxnId(),
                    updateTransactionRequest.getStockItemId(),
                    todayDT,
                    updateTransactionRequest.getTxnType(),
                    updateTransactionRequest.getPreTxnStockCount(),
                    updateTransactionRequest.getPostTxnStockCount(),
                    (int) Double.parseDouble(updateTransactionRequest.getTxnUnitPrice()),
                    updateTransactionRequest.getStockTxnEntity(),
                    updateTransactionRequest.getSourceTxnEntityId(),
                    updateTransactionRequest.getSourceEntityName(),
                    Double.parseDouble(updateTransactionRequest.getTxnUnitPrice())
                    );
            return new ResponseModelBoolean(true);
        }
        return new ResponseModelBoolean(false);
    }
}

