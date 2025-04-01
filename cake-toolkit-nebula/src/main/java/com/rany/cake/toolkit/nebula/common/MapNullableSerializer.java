package com.rany.cake.toolkit.nebula.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;

public class MapNullableSerializer extends JsonSerializer<HashMap<String, Object>> {

    @Override
    public void serialize(HashMap<String, Object> hashMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(new JSONObject(hashMap));
    }
}