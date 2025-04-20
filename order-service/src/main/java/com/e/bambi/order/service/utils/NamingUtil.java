package com.e.bambi.order.service.utils;

public class NamingUtil {
    public static String toSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
