package com.tencent.common.dto.common;

import org.springframework.data.domain.Sort.Direction;

/**
 * Sort
 *
 * @author torrisli
 * @date 2021/4/23
 * @Description: Sort
 */
public class Sort {

    public String sortKey;

    public Direction sortOrder;

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public Direction getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Direction sortOrder) {
        this.sortOrder = sortOrder;
    }
}
