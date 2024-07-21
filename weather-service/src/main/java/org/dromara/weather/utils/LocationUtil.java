package org.dromara.weather.utils;

import org.dromara.common.core.utils.StringUtils;

public class LocationUtil {

    public static String isValidLocation(String lon, String lat) {
        if (lat == null || lon == null || StringUtils.isEmpty(lat) || StringUtils.isEmpty(lon)) {
            return null;
        }

        // 中国经纬度校验
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        if (!(longitude >= 73.55 && longitude <= 135.05 && latitude >= 3.75 && latitude <= 53.55)) {
            return null;
        }

        return lon + "," + lat;
    }

    public static String isValidCityCode(String cityCode) {
        return cityCode;
    }
}
