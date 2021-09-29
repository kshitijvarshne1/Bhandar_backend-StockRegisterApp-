/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 2:19 PM
 *   File: StoreDao.java
 */

package com.stockregisterapp.v3API.dao;

import com.stockregisterapp.entity.StockTransaction;
import com.stockregisterapp.entity.Store;
import com.stockregisterapp.repository.StoreRepository;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModel;
import com.stockregisterapp.v2API.model.ItemTransactionModelV2;
import com.stockregisterapp.v2API.model.StoreReportModelV2;
import com.stockregisterapp.v3API.entity.StockItemV3;
import com.stockregisterapp.v3API.entity.StockTransactionV3;
import com.stockregisterapp.v3API.model.StoreModelRequestV3;
import com.stockregisterapp.v3API.repository.StockItemRepositoryV3;
import com.stockregisterapp.v3API.repository.StockTransactionRepositoryV3;
import com.stockregisterapp.v3API.returnModel.ItemTransactionModelV3;
import com.stockregisterapp.v3API.returnModel.StoreModelV3;
import com.stockregisterapp.v3API.returnModel.StoreReportModelV3;
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
public class StoreDaoV3 {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StockItemRepositoryV3 stockItemRepositoryV3;

    @Autowired
    StockTransactionRepositoryV3 stockTransactionRepositoryV3;

    public ResponseModel createStore(StoreModelRequestV3 storeModelRequestV3) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        Store s = storeRepository.save(new Store(storeModelRequestV3.getName(), storeModelRequestV3.getBusinessId(), todayDT, todayDT, storeModelRequestV3.getStoreType(), storeModelRequestV3.getStockEntryType()));
        return new ResponseModel(s.getStoreId());
    }

    public List<StockItemV3> getAllStockItemsV3(String storeId) {
        return stockItemRepositoryV3.findByStoreId(storeId);
    }

    public List<StockTransactionV3> getAllStockTransactionV3(List<String> itemIds) {
        return stockTransactionRepositoryV3.findByStockItemIdsV3(itemIds);
    }

    public StoreReportModelV3 getReport(String storeId, String filterDuration, String filterStartValue) throws ParseException {
        filterStartValue = filterStartValue.replaceAll("-", "/");
        List<StockItemV3> stockItemV3List = getAllStockItemsV3(storeId);

        HashMap<String, StockItemV3> stringStockItemV3HashMap = (HashMap<String, StockItemV3>) stockItemV3List.stream().collect(Collectors.toMap(StockItemV3::getStockItemId, Function.identity()));
        List<String> itemIds = stringStockItemV3HashMap.keySet().stream().collect(Collectors.toList());
        List<StockTransactionV3> stockTransactionV3List = getAllStockTransactionV3(itemIds);

        List<ItemTransactionModelV3> itemTransactionModelV3List = new ArrayList<>();
        for (String s : stringStockItemV3HashMap.keySet()) {
            itemTransactionModelV3List.add(getReportForItem(stringStockItemV3HashMap.get(s), stockTransactionV3List, filterStartValue, filterDuration));
        }
        itemTransactionModelV3List.sort(new Comparator<ItemTransactionModelV3>() {
            @Override
            public int compare(ItemTransactionModelV3 o1, ItemTransactionModelV3 o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });
        StoreReportModelV3 stm = new StoreReportModelV3(itemTransactionModelV3List);

        return stm;
    }

    private ItemTransactionModelV3 getReportForItem(StockItemV3 stockItemV3, List<StockTransactionV3> allTransactions, String startDate, String filterDuration) throws ParseException {
        ItemTransactionModelV3 itemTransactionModelV3 = new ItemTransactionModelV3();
        List<StockTransactionV3> itemTransactions = new ArrayList<>();
        for (StockTransactionV3 txn : allTransactions) {
            if (txn.getStockItemId().equalsIgnoreCase(stockItemV3.getStockItemId())) {
                itemTransactions.add(txn);
            }
        }
        List<StockTransactionV3> filteredTransaction = filterTransaction(itemTransactions, filterDuration, startDate);
        float inCount = 0;
        float outCount = 0;
        String openingStock = "";
        String closingStock = "";
        float currentStockCount = 0;
        String lastUpdatedTime = "";
        int i = 0;
        Collections.sort(filteredTransaction, new Comparator<StockTransactionV3>() {
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            public int compare(StockTransactionV3 o1, StockTransactionV3 o2) {
                try {
                    return formatter.parse(o1.getTransactionTs()).compareTo(formatter.parse(o2.getTransactionTs()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        for (StockTransactionV3 st : filteredTransaction) {
            switch (st.getTxnType()) {
                case "IN":
                    float temp = Math.max(0, Float.parseFloat(String.valueOf(st.getClosingStock())) - Float.parseFloat(String.valueOf(st.getOpeningStock())));
                    inCount += temp;
                    break;
                case "OUT":
                    float tempO = Math.max(0, Float.parseFloat(String.valueOf(st.getOpeningStock())) - Float.parseFloat(String.valueOf(st.getClosingStock())));
                    outCount += tempO;
                    break;
                default:
                    break;
            }
            if (i == 0) {
                if (st.getTxnType().equalsIgnoreCase("IN") && st.getDestinationTxnEntityId().equalsIgnoreCase("STORE") && st.getDestinationTxnEntityId().equalsIgnoreCase("NULL")) {
                    itemTransactionModelV3.setOpeningStock("-");
                    float temp = Math.max(0, Float.parseFloat(String.valueOf(st.getClosingStock())) - Float.parseFloat(String.valueOf(st.getOpeningStock())));
                    inCount -= temp;
                } else {
                    itemTransactionModelV3.setOpeningStock(String.valueOf(st.getOpeningStock()));
                }
            }

            if (i == filteredTransaction.size() - 1) {
                itemTransactionModelV3.setClosingStock(String.valueOf(st.getClosingStock()));
                itemTransactionModelV3.setLastUpdatedTime(st.getTransactionTs());
            }
            i++;
        }
        if (filteredTransaction.size() == 0) {
            StockTransactionV3 stockTransaction = getOneLastTransaction(itemTransactions, startDate);
            if (stockTransaction == null) {
                itemTransactionModelV3.setOpeningStock("-");
                itemTransactionModelV3.setClosingStock("-");
                itemTransactionModelV3.setLastUpdatedTime("");
            } else {
                itemTransactionModelV3.setOpeningStock(String.valueOf(stockTransaction.getOpeningStock()));
                itemTransactionModelV3.setClosingStock(String.valueOf(stockTransaction.getOpeningStock()));
                itemTransactionModelV3.setLastUpdatedTime(stockTransaction.getTransactionTs());
            }
        }
        itemTransactionModelV3.setLastUpdatedTime(setTime(itemTransactionModelV3.getLastUpdatedTime()));
        itemTransactionModelV3.setItemId(stockItemV3.getStockItemId());
        itemTransactionModelV3.setItemName(stockItemV3.getName());
        itemTransactionModelV3.setPriceUnit(stockItemV3.getItemUnit());

        itemTransactionModelV3.setCurrentStockCount(String.valueOf(stockItemV3.getAvailableCount()));

        return itemTransactionModelV3;
    }

    private StockTransactionV3 getOneLastTransaction(List<StockTransactionV3> allTransactions, String startDate) {
        Collections.sort(allTransactions, new Comparator<StockTransactionV3>() {
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            public int compare(StockTransactionV3 o1, StockTransactionV3 o2) {
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

        for (StockTransactionV3 transaction : allTransactions) {
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

    private List<StockTransactionV3> filterTransaction(List<StockTransactionV3> stockTransactionList, String filterDuration, String filterStartValue) {
        List<StockTransactionV3> finalRes = new ArrayList<>();
        if (filterDuration.equalsIgnoreCase("Daily")) {
            for (StockTransactionV3 s : stockTransactionList) {
                if (s.getTransactionTs().substring(0, 10).equalsIgnoreCase(filterStartValue)) {
                    finalRes.add(s);
                }
            }
        } else if (filterDuration.equalsIgnoreCase("Monthly")) {
            String year = filterStartValue.substring(6);
            String month = filterStartValue.substring(3, 5);
            for (StockTransactionV3 s : stockTransactionList) {
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


            for (StockTransactionV3 s : stockTransactionList) {
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
}

