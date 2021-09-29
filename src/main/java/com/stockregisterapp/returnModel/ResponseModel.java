/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 18-Jul-21
 *   Time: 7:00 PM
 *   File: ResponseModel.java
 */

package com.stockregisterapp.returnModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {
    private String response;

    public ResponseModel(String response) {
        this.response = response;
    }
}


