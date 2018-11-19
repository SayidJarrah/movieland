package com.dkorniichuk.movieland.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;

public class JsonHelper {
    public static JsonNode merge(JsonNode nodeTo, JsonNode nodeFrom) {
        Iterator<String> fieldNames = nodeFrom.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode jsonNode = nodeTo.get(fieldName);
            if (jsonNode != null && jsonNode.isObject()) {
                merge(jsonNode, nodeFrom.get(fieldName));
            } else {
                if(nodeTo instanceof ObjectNode){
                    JsonNode value = nodeFrom.get(fieldName);
                    ((ObjectNode) nodeTo).put(fieldName,value);
                }
            }
        }
        return nodeTo;
    }

}
