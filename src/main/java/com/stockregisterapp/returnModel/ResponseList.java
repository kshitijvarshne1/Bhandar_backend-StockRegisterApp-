/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 17-Jul-21
 *   Time: 12:15 PM
 *   File: ListOfStoreModel.java
 */

package com.stockregisterapp.returnModel;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseList<T> {
    private List<T> response;

    public ResponseList(List<T> response) {
        this.response = response;
    }

}

