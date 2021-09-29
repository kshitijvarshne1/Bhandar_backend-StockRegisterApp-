/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 30-Aug-21
 *   Time: 2:41 PM
 *   File: StoreDaoV2.java
 */

package com.stockregisterapp.v2API.dao;

import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.entity.StockTransaction;
import com.stockregisterapp.model.ItemTransactionModel;
import com.stockregisterapp.model.StoreReportModel;
import com.stockregisterapp.repository.PartyRepository;
import com.stockregisterapp.repository.StockItemRepository;
import com.stockregisterapp.repository.StockTransactionRepository;
import com.stockregisterapp.repository.StoreRepository;
import com.stockregisterapp.v2API.model.ItemTransactionModelV2;
import com.stockregisterapp.v2API.model.StoreReportModelV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StoreDaoV2 {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    PartyRepository partyRepository;
    @Autowired
    StockItemRepository stockItemRepository;
    @Autowired
    StockTransactionRepository stockTransactionRepository;

    public StoreReportModelV2 getReport2(String storeId, String filterDuration, String filterStartValue) throws ParseException {
        filterStartValue = filterStartValue.replaceAll("-", "/");

        List<StockItem> stockItemList = getAllStockItems(storeId);


        // HashMap<StockItemId, StockItem>
        HashMap<String, StockItem> stringStockItemHashMap = (HashMap<String, StockItem>) stockItemList.stream().collect(Collectors.toMap(StockItem::getStockItemId, Function.identity()));

        List<String> itemIds = stringStockItemHashMap.keySet().stream().collect(Collectors.toList());
        List<StockTransaction> stockTransactionList = getAllStockTransaction(itemIds);


        List<ItemTransactionModelV2> itemTransactionModelList = new ArrayList<>();
        for (String s : stringStockItemHashMap.keySet()) {
            itemTransactionModelList.add(getReportForItem(stringStockItemHashMap.get(s), stockTransactionList, filterStartValue, filterDuration));
        }

        float totalIn = 0;
        float totalOut = 0;
        for (ItemTransactionModelV2 itml : itemTransactionModelList) {
            totalIn += Float.parseFloat(itml.getTotalInPrice());
            totalOut += Float.parseFloat(itml.getTotalOutPrice());
        }

        itemTransactionModelList.sort(new Comparator<ItemTransactionModelV2>() {
            @Override
            public int compare(ItemTransactionModelV2 o1, ItemTransactionModelV2 o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });

        StoreReportModelV2 stm = new StoreReportModelV2(String.valueOf(totalIn), String.valueOf(totalOut), itemTransactionModelList);


        return stm;
    }

    private List<StockTransaction> getAllStockTransaction(List<String> itemIds) {
        return stockTransactionRepository.findByStockItemIds(itemIds);
    }


    private ItemTransactionModelV2 getReportForItem(StockItem stockItem, List<StockTransaction> allTransactions, String startDate, String filterDuration) throws ParseException {
        ItemTransactionModelV2 itemTransactionModel = new ItemTransactionModelV2();
        /*  List<StockTransaction> itemTransactions =allTransactions.stream().filter(txn -> txn.getStockItemId().equals(stockItem.getStockItemId())).collect(Collectors.toList());
         */

        List<StockTransaction> itemTransactions = new ArrayList<>();
        for (StockTransaction txn : allTransactions) {
            if (txn.getStockItemId().equalsIgnoreCase(stockItem.getStockItemId())) {
                itemTransactions.add(txn);
            }
        }


        List<StockTransaction> filterdTransaction = filterTransaction(itemTransactions, filterDuration, startDate);


        float inCount = 0;
        float outCount = 0;
        String openingStock = "";
        String closingStock = "";
        float currentStockCount = 0;
        String lastUpdatedTime = "";
        double totalInPrice = 0;
        double totalOutPrice = 0;

        int i = 0;
        Collections.sort(filterdTransaction, new Comparator<StockTransaction>() {
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            public int compare(StockTransaction o1, StockTransaction o2) {
                try {
                    return formatter.parse(o1.getTransactionTs()).compareTo(formatter.parse(o2.getTransactionTs()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        for (StockTransaction st : filterdTransaction) {
            switch (st.getTxnType()) {
                case "IN":
                    float temp = Math.max(0, Float.parseFloat(st.getPostTxnStockCount()) - Float.parseFloat(st.getPreTxnStockCount()));
                    inCount += temp;
                    totalInPrice += temp * st.getTxnUnitPriceF();
                    break;
                case "OUT":
                    float tempO = Math.max(0, Float.parseFloat(st.getPreTxnStockCount()) - Float.parseFloat(st.getPostTxnStockCount()));
                    outCount += tempO;
                    totalOutPrice += tempO * st.getTxnUnitPriceF();
                    break;
                default:
                    break;
            }

            if (i == 0) {
                if (st.getTxnType().equalsIgnoreCase("IN") && st.getStockTxnEntity().equalsIgnoreCase("STORE") && st.getSourceTxnEntityId().equalsIgnoreCase("NULL")) {
                    itemTransactionModel.setOpeningStock("-");
                    float temp = Math.max(0, Float.parseFloat(st.getPostTxnStockCount()) - Float.parseFloat(st.getPreTxnStockCount()));
                    inCount -= temp;
                    totalInPrice -= temp * st.getTxnUnitPriceF();
                } else {
                    itemTransactionModel.setOpeningStock(st.getPreTxnStockCount());
                }
            }

            if (i == filterdTransaction.size() - 1) {
                itemTransactionModel.setClosingStock(st.getPostTxnStockCount());
                itemTransactionModel.setLastUpdatedTime(st.getTransactionTs());
            }
            i++;
        }

        if (filterdTransaction.size() == 0) {
            StockTransaction stockTransaction = getOneLastTransaction(itemTransactions, startDate);
            if (stockTransaction == null) {
                itemTransactionModel.setOpeningStock("-");
                itemTransactionModel.setClosingStock("-");
                itemTransactionModel.setLastUpdatedTime("");
            } else {
                itemTransactionModel.setOpeningStock(stockTransaction.getPostTxnStockCount());
                itemTransactionModel.setClosingStock(stockTransaction.getPostTxnStockCount());
                itemTransactionModel.setLastUpdatedTime(stockTransaction.getTransactionTs());
            }
        }

        itemTransactionModel.setLastUpdatedTime(setTime(itemTransactionModel.getLastUpdatedTime()));


        itemTransactionModel.setItemId(stockItem.getStockItemId());
        itemTransactionModel.setItemName(stockItem.getName());
        itemTransactionModel.setPriceUnit(stockItem.getPriceUnit());
        itemTransactionModel.setTotalIn(String.valueOf(inCount));
        itemTransactionModel.setTotalOut(String.valueOf(outCount));
        itemTransactionModel.setCurrentStockCount(String.valueOf(stockItem.getAvailableCountF()));
        itemTransactionModel.setSellingPrice(String.valueOf(Float.parseFloat(stockItem.getSellingPrice())));
        itemTransactionModel.setPurchasePrice(String.valueOf(Float.parseFloat(stockItem.getPurchasePrice())));
        itemTransactionModel.setTotalInPrice(String.valueOf(totalInPrice));
        itemTransactionModel.setTotalOutPrice(String.valueOf(totalOutPrice));

        return itemTransactionModel;


    }

    private List<StockItem> getAllStockItems(String storeId) {
        return stockItemRepository.findByStoreId(storeId);
    }


    public List<StockTransaction> filterTransaction(List<StockTransaction> stockTransactionList, String filterDuration, String filterStartValue) {
        List<StockTransaction> finalRes = new ArrayList<>();
        if (filterDuration.equalsIgnoreCase("Daily")) {
            for (StockTransaction s : stockTransactionList) {
                if (s.getTransactionTs().substring(0, 10).equalsIgnoreCase(filterStartValue)) {
                    finalRes.add(s);
                }
            }
        } else if (filterDuration.equalsIgnoreCase("Monthly")) {
            String year = filterStartValue.substring(6);
            String month = filterStartValue.substring(3, 5);
            for (StockTransaction s : stockTransactionList) {
                String y = s.getTransactionTs().substring(6, 10);
                String m = s.getTransactionTs().substring(3, 5);
                if (y.equalsIgnoreCase(year) && m.equalsIgnoreCase(month)) {
                    finalRes.add(s);
                }
            }
        } else if (filterDuration.equalsIgnoreCase("Weekly")) {
            String day = filterStartValue.substring(0, 2);
            String month = filterStartValue.substring(3, 5);
            String year = filterStartValue.substring(6);
            LocalDate today = LocalDate.parse(year + "-" + month + "-" + day);
            LocalDate monday = today;
            while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
                monday = monday.minusDays(1);
            }
            LocalDate sunday = today;
            while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
                sunday = sunday.plusDays(1);
            }
              /*  System.out.println("Monday of the Week: " + monday);
                System.out.println("Sunday of the Week: " + sunday);*/
            int year1 = Integer.parseInt(monday.toString().substring(0, 4));
            int month1 = Integer.parseInt(monday.toString().substring(5, 7));
            int day1 = Integer.parseInt(monday.toString().substring(8));

            int year2 = Integer.parseInt(sunday.toString().substring(0, 4));
            int month2 = Integer.parseInt(sunday.toString().substring(5, 7));
            int day2 = Integer.parseInt(sunday.toString().substring(8));


            for (StockTransaction s : stockTransactionList) {
                int y = Integer.parseInt(s.getTransactionTs().substring(6, 10));
                int m = Integer.parseInt(s.getTransactionTs().substring(3, 5));
                int d = Integer.parseInt(s.getTransactionTs().substring(0, 2));
                if (month1 == month2) {
                    if (month1 == m && year1 == y && d >= day1 && d <= day2) {
                        finalRes.add(s);
                    }
                } else if (m == month1 && year1 == y && d >= day1) {
                    finalRes.add(s);
                } else if (m == month2 && year2 == y && d <= day2) {
                    finalRes.add(s);
                }
            }
        } else {
            return new ArrayList<>();
        }
        return finalRes;
    }

    public StockTransaction getOneLastTransaction(List<StockTransaction> allTransactions, String startDate) {
        Collections.sort(allTransactions, new Comparator<StockTransaction>() {
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


        LocalDateTime startLocalDateTime = LocalDateTime.of(Integer.parseInt(startDate.substring(6, 10)),
                Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(0, 2)),
                00, 00, 00);

        for (StockTransaction transaction : allTransactions) {
            String dts = transaction.getTransactionTs();
            LocalTime localTime = LocalTime.of(Integer.parseInt(dts.substring(11, 13)), Integer.parseInt(dts.substring(15, 16)), Integer.parseInt(dts.substring(17, 19)));
            LocalDate localDate = LocalDate.of(Integer.parseInt(dts.substring(6, 10)), Integer.parseInt(dts.substring(3, 5)), Integer.parseInt(dts.substring(0, 2)));
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);


            if (localDateTime.isBefore(startLocalDateTime)) {
                return transaction;
            }

        }
        return null;
    }

    private String setTime(String lastUpdatedTime) throws ParseException {
        String time = "";
        if (lastUpdatedTime.length() < 10) {
            time = "";
        } else {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
            String todayDT = dateFormat.format(date);
            String newRes = "";
            if (lastUpdatedTime.length() > 9) {
                newRes = lastUpdatedTime.substring(0, 10);
            }
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String firstInput = newRes;
            String secondInput = todayDT.substring(0, 10);
            LocalDate firstDate = LocalDate.parse(firstInput, formatter2);
            LocalDate secondDate = LocalDate.parse(secondInput, formatter2);
            long days = ChronoUnit.DAYS.between(firstDate, secondDate);
            if (days <= 0) {
                String result = calculateTime(lastUpdatedTime.substring(11));
                time = result;
            } else {
                time = "Updated " + days + " days ago";
            }
        }
        return time;
    }

    public String calculateTime(String s1) throws ParseException {
        String time1 = s1;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("IST"));
        String res = formatter.format(date);
        String time2 = res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        Date date1 = simpleDateFormat.parse(time1);
        Date date2 = simpleDateFormat.parse(time2);

        long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());

        long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;

        long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;

        long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;

        if (differenceInHours >= 1) {
            return "updated " + differenceInHours + " hours ago";
        }
        return "updated " + differenceInMinutes + " minutes ago";
    }

    public Boolean updateStoreName(String storeId, String storeName) {
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
            String todayDT = dateFormat.format(date);
            storeRepository.updateStoreName(storeId, storeName, todayDT);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

