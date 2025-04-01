package com.rany.cake.toolkit.nebula.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rany.cake.toolkit.nebula.common.MapNullableSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseGraphModel implements Serializable {
    private String type;
    @JsonSerialize(using = MapNullableSerializer.class)
    private Map<String,Object> map;
}