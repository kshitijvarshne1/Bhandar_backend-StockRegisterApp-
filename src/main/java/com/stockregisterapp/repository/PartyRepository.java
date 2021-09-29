package com.stockregisterapp.repository;

import com.stockregisterapp.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, String> {
    @Query("select p from Party p where p.businessId= :businessId and isDelete = false")
    List<Party> findByBusinessId(@Param("businessId") String businessId);

    @Transactional
    @Modifying
    @Query("update Party p set p.name=:partyName, p.mobile=:partyPhone, p.updatedAt=:todayDT where p.partyId=:partyId")
    void updatePartyInfo(String partyId, String partyName, String partyPhone, String todayDT);

    @Transactional
    @Modifying
    @Query("update Party p set p.isDelete=:isDelete where p.partyId=:partyId")
    void deleteParty(String partyId, boolean isDelete);
}
