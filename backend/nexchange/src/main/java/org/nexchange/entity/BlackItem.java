package org.nexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BlackItem {
    private String token;
    private Date expire;

    @Override
    public boolean equals(Object another) {
        BlackItem item = (BlackItem) another;
        return this.expire.equals(item.getExpire());
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}
