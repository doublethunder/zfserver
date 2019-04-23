package com.dt.miniapp.util;

public class JsonpString {
    private static final String JSONP_FORMAT = "%s(%s)";

    public static String jsonp(String data, String callback) {
        return String.format(JSONP_FORMAT, callback, data);
    }
}
