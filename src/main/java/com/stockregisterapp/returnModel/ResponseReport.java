/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 23-Jul-21
 *   Time: 5:57 PM
 *   File: ResponseReport.java
 */

package com.stockregisterapp.returnModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseReport<T> {
    private T response;

    public ResponseReport(T response) {
        this.response = response;
    }

}

