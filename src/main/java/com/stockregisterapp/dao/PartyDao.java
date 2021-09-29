/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 06-Jul-21
 *   Time: 5:30 PM
 *   File: PartyDao.java
 */

package com.stockregisterapp.dao;


import com.stockregisterapp.entity.Party;
import com.stockregisterapp.entity.StockItem;
import com.stockregisterapp.entity.StockTransaction;
import com.stockregisterapp.model.PartyModel;
import com.stockregisterapp.model.PartyReport;
import com.stockregisterapp.model.PartyTransactionReport;
import com.stockregisterapp.repository.PartyRepository;
import com.stockregisterapp.repository.StockItemRepository;
import com.stockregisterapp.repository.StockTransactionRepository;
import com.stockregisterapp.returnModel.ResponseList;
import com.stockregisterapp.returnModel.ResponseModelBoolean;
import com.stockregisterapp.returnModel.ResponseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PartyDao {

    @Autowired
    PartyRepository partyRepository;
    @Autowired
    StockTransactionRepository stockTransactionRepository;
    @Autowired
    StockItemRepository stockItemRepository;

    public String createParty(Party party) {
        Party p = partyRepository.save(party);
        return p.getPartyId();
    }

    // sort according to createdAT time
    public ResponseList<PartyModel> getParty(String business_id) {
        List<Party> l = partyRepository.findByBusinessId(business_id);
        Collections.sort(l, new Comparator<Party>() {
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            public int compare(Party o1, Party o2) {
                try {
                    return formatter.parse(o1.getCreatedAt()).compareTo(formatter.parse(o2.getCreatedAt()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        l.sort(new Comparator<Party>() {
            @Override
            public int compare(Party o1, Party o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        List<PartyModel> resL = new ArrayList<>();
        for (Party party : l) {
                resL.add(new PartyModel(party.getPartyId(), party.getName(), party.getMobile(), party.getBusinessId()));
        }
        return new ResponseList<>(resL);
    }

    public ResponseModelBoolean updatePartyInfo(String partyId, String partyName, String partyPhone) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        String todayDT = dateFormat.format(date);
        try {
            partyRepository.updatePartyInfo(partyId, partyName, partyPhone, todayDT);
            return new ResponseModelBoolean(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseModelBoolean(false);
    }

    public ResponseModelBoolean deleteParty(String partyId) {
        try {
            boolean isDelete = true;
            partyRepository.deleteParty(partyId, isDelete);
            return new ResponseModelBoolean(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseModelBoolean(false);
    }

    public ResponseReport<PartyReport> reportPartyTransactions(String storeId, String partyId) {
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findByPartyId(partyId);
        List<StockItem> stockItemList = stockItemRepository.findByStoreId(storeId);

        List<String> stockItemIds = new ArrayList<>();
        HashMap<String, String> mp = new HashMap<>();
        for (StockItem ss : stockItemList) {
            stockItemIds.add(ss.getStockItemId());
            mp.put(ss.getStockItemId(),ss.getName());
        }
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
        long totalStockInValue = (long) 0.0;
        long totalStockOutValue = (long) 0.0;
        List<PartyTransactionReport> res = new ArrayList<>();
        for (StockTransaction st : stockTransactionList) {
            if (stockItemIds.contains(st.getStockItemId())) {
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
                double stockValue = 0.0;
                switch (st.getTxnType()) {
                    case "IN":
                        stockValue = (double) st.getTxnUnitPriceF() *
                                (
                                        Double.parseDouble(st.getPostTxnStockCount()) -
                                                Double.parseDouble(st.getPreTxnStockCount())
                                );
                        totalStockInValue += stockValue;
                        break;
                    case "OUT":
                        stockValue = (double) st.getTxnUnitPriceF() *
                                (
                                        Double.parseDouble(st.getPreTxnStockCount()) -
                                                Double.parseDouble(st.getPostTxnStockCount())
                                );
                        totalStockOutValue += stockValue;
                        break;
                    default:
                        break;
                }
                res.add(
                        new PartyTransactionReport(
                                mp.get(st.getStockItemId()),
                                pk, String.valueOf(st.getTxnUnitPriceF()),
                                st.getPostTxnStockCount(),
                                String.valueOf(stockValue),
                                st.getTxnType()
                        )
                );

            }
        }


        return new ResponseReport<>(
                new PartyReport(
                        String.valueOf(totalStockInValue),
                        String.valueOf(totalStockOutValue),
                        res
                )
        );
    }

    private String setMonth(String pk) {
        String[] months = {"Jan", "Feb", "Mar", "Api", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String monthsStr = pk.substring(3, 5);
        String MM = months[Integer.parseInt(monthsStr) - 1];
        String newStr = pk.substring(0, 2) + " " + MM + ", " + pk.substring(6);
        return newStr;
    }
}

