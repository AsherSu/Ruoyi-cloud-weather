package org.dromara.weather.utils;

import org.dromara.common.json.utils.JsonUtils;
import org.dromara.weather.domain.hefeng.common.WeatherResponse;

import java.util.Map;

public class HefengWeatherUtil {
    private static String domain = DynamicProperties.getProperty("weather.hefeng.domain");

    private static String key = DynamicProperties.getProperty("weather.hefeng.key");

    private static String now = DynamicProperties.getProperty("weather.hefeng.now");

    private static String minutely5m = DynamicProperties.getProperty("weather.hefeng.minutely5m");

    private static String forecast24h = DynamicProperties.getProperty("weather.hefeng.forecast24h");

    private static String forecast3d = DynamicProperties.getProperty("weather.hefeng.forecast3d");

    private static String forecast7d = DynamicProperties.getProperty("weather.hefeng.forecast7d");

    private static String gridNow = DynamicProperties.getProperty("weather.hefeng.gridNow");

    private static String warning = DynamicProperties.getProperty("weather.hefeng.warning");

    public static String getNowWeather(String city) {
        String url = domain + now;
        url = PlaceholderUtil.replacePlaceholders(url, Map.of("city", city, "key", key));
        // 请求接口获取天气信息
        return HttpClientUtil.get(url);

    }

    public static String getForecast3dWeather(String city) {
        String url = domain + forecast3d;
        url = PlaceholderUtil.replacePlaceholders(url, Map.of("city", city, "key", key));
        // 请求接口获取天气信息
        return HttpClientUtil.get(url);
    }

    public static String getForecast7dWeather(String city) {
        String url = domain + forecast7d;
        url = PlaceholderUtil.replacePlaceholders(url, Map.of("city", city, "key", key));
        // 请求接口获取天气信息
        return HttpClientUtil.get(url);
    }

    public static String getMinutely5mWeather(String location) {
        String url = domain + minutely5m;
        url = PlaceholderUtil.replacePlaceholders(url, Map.of("location", location, "key", key));
        // 请求接口获取天气信息
        return HttpClientUtil.get(url);
    }

    public static String getGridNowWeather(String location) {
        String url = domain + gridNow;
        url = PlaceholderUtil.replacePlaceholders(url, Map.of("location", location, "key", key));
        // 请求接口获取天气信息
        return HttpClientUtil.get(url);
    }

    public static String getWarningWeather(String city) {
        String url = domain + warning;
        url = PlaceholderUtil.replacePlaceholders(url, Map.of("city", city, "key", key));
        // 请求接口获取天气信息
        return HttpClientUtil.get(url);
    }
}
