/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 18-Jul-21
 *   Time: 9:06 PM
 *   File: ResponseModelBoolean.java
 */

package com.stockregisterapp.returnModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModelBoolean {
    private boolean response;

    public ResponseModelBoolean(boolean response) {
        this.response = response;
    }
}

