/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 11:11 AM
 *   File: Store.java
 */

package com.stockregisterapp.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String storeId;
    private String name;
    private String businessId;
    private String createdAt;
    private String updatedAt;
    private String storeType;
    private String stockEntryType;


    public Store(String name, String businessId, String createdAt, String updatedAt) {
        this.name = name;
        this.businessId = businessId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Store(String name, String businessId, String createdAt, String updatedAt, String storeType, String stockEntryType) {
        this.name = name;
        this.businessId = businessId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.storeType = storeType;
        this.stockEntryType = stockEntryType;
    }

    public Store() {
    }
}

