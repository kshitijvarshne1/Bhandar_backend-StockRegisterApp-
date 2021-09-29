/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 05-Jul-21
 *   Time: 11:13 AM
 *   File: Party.java
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
public class Party {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String partyId;
    private String name;
    private String mobile;
    private String businessId;
    private String createdAt;
    private String updatedAt;
    private boolean isDelete;

    public Party(String name, String mobile, String businessId, String createdAt, String updatedAt, boolean isDelete) {
        this.name = name;
        this.mobile = mobile;
        this.businessId = businessId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDelete = isDelete;
    }

    public Party() {
    }
}

