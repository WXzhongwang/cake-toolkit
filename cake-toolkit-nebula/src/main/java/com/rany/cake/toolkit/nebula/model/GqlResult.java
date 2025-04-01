package com.rany.cake.toolkit.nebula.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lxy
 */
@Getter
@Setter
public class GqlResult {
    private final List<String> headers;
    private List<Map<String, Object>> tables;
    private Integer timeCost;

    public GqlResult(List<String> headers) {
        this.headers = headers;
    }

    public void addRow(Map<String, Object> row) {
        if (row == null) {
            return;
        }
        if (tables == null) {
            tables = new ArrayList<>();
        }
        tables.add(row);
    }
}
