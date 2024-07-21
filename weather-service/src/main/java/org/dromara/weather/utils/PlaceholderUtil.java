package org.dromara.weather.utils;

import java.util.Map;

public class PlaceholderUtil {

    public static String replacePlaceholders(String input, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            input = input.replace("{" + key + "}", value);
        }
        return input;
    }
}
