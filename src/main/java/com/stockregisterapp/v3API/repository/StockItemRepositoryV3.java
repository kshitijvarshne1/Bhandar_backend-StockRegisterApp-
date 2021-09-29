package com.stockregisterapp.v3API.repository;


import com.stockregisterapp.v3API.entity.StockItemV3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockItemRepositoryV3 extends JpaRepository<StockItemV3, String> {


    @Query("select s from StockItemV3 s where s.storeId=:storeId")
    List<StockItemV3> findByStoreId(@Param("storeId") String storeId);

}
