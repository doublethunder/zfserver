package com.dt.miniapp.util;

import com.dt.miniapp.exception.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenlei
 * @date 2018-08-31
 */
@Slf4j
public class JsonConvert {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    private static ObjectMapper getMapper() {
        return mapper;
    }

    public static <T> T deserializeObject(String jsonStr, TypeReference<T> type) {
        T userData;
        try {
            userData = getMapper().readValue(jsonStr, type);
            return userData;
        } catch (Exception e) {
            String errmsg = "deserializeObject:" + jsonStr + ", type:" + type.getType().getTypeName() + " error.";
            throw new JsonParseException(errmsg, e);
        }
    }

    public static <T> List<T> deserializeJsonToList(String jsonStr, Class<T> clazz) {
        List<T> list;
        try {
            list = getMapper().readValue(jsonStr, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
            return list;
        } catch (Exception e) {
            String errmsg = "deserializeObject:" + jsonStr + ", type:" + clazz.getSimpleName() + " error.";
            throw new JsonParseException(errmsg, e);
        }
    }

    public static Object convertObject(Object orig, Class<?> type) {
        Object userData;
        userData = getMapper().convertValue(orig, type);
        return userData;
    }

    public static <T> T deserializeObject(String jsonStr, Class<T> clazz) {
        T userData;
        try {
            if (StringUtils.isEmpty(jsonStr)) {
                return null;
            }
            userData = getMapper().readValue(jsonStr, clazz);
            return userData;
        } catch (Exception e) {
            String errmsg = "deserializeObject:" + jsonStr + ", type:" + clazz.getSimpleName() + " error.";
            throw new JsonParseException(errmsg, e);
        }
    }

    public static Map<?, ?> deserializeObject(String jsonStr) {
        Map<String, Object> userData;
        try {
            userData = getMapper().readValue(jsonStr, Map.class);
            return userData;
        } catch (Exception e) {
            String errmsg = "deserializeObject:" + jsonStr + ", type:Map error.";
            throw new JsonParseException(errmsg, e);
        }
    }

    public static String serializeObject(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (Exception e) {
            String errmsg = "deserializeObject:" + object + " error.";
            throw new JsonParseException(errmsg, e);
        }
    }

    public static <T, E> Map<T, E> deserializeJsonToMap(String jsonStr, Class<T> keyType,
                                                        Class<E> valueType) {
        try {
            Map<T, E> map = getMapper().readValue(jsonStr,
                    TypeFactory.defaultInstance().constructMapType(HashMap.class, keyType, valueType));
            return map;
        } catch (Exception e) {
            String errmsg = "deserializeObject:" + jsonStr + " error.";
            throw new JsonParseException(errmsg, e);
        }
    }
}
