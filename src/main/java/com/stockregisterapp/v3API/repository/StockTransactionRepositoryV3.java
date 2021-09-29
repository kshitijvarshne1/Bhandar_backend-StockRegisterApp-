/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 16-Sep-21
 *   Time: 3:09 PM
 *   File: StockTransactionRepositoryV3.java
 */

package com.stockregisterapp.v3API.repository;

import com.stockregisterapp.v3API.entity.StockTransactionV3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTransactionRepositoryV3 extends JpaRepository<StockTransactionV3, String> {

    @Query("select s from StockTransactionV3 s where s.stockItemId IN (:itemIds)")
    List<StockTransactionV3> findByStockItemIdsV3(@Param("itemIds") List<String> itemIds);

}

