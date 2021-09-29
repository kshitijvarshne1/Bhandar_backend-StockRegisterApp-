/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 12:38 PM
 *   File: StoreRepository.java
 */

package com.stockregisterapp.repository;


import com.stockregisterapp.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    @Query("select s from Store s where s.businessId= :businessId")
    List<Store> findByBusinessId(@Param("businessId") String businessId);

    @Query("select count(s) from Store s where s.storeId=:storeId")
    int countItems(@Param("storeId") String storeId);

    @Transactional
    @Modifying
    @Query("update Store s set s.name=:storeName, s.updatedAt=:todayDT where s.storeId=:storeId")
    void updateStoreName(String storeId, String storeName, String todayDT);
}

