package org.dromara.weather.service;

import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.weather.domain.common.ErrorCode;
import org.dromara.weather.domain.common.Result;
import org.dromara.weather.domain.hefeng.common.HefengRedis;
import org.dromara.weather.domain.hefeng.common.WeatherResponse;
import org.dromara.weather.domain.hefeng.daily.HefengDaily;
import org.dromara.weather.domain.hefeng.minutely.HefengMinutely;
import org.dromara.weather.domain.hefeng.now.HefengNow;
import org.dromara.weather.domain.hefeng.warning.HefengWarning;
import org.dromara.weather.utils.HefengWeatherUtil;
import org.dromara.weather.utils.LocationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class HefengWeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HefengWeatherService.class);

    public List<HefengMinutely> getMinutely5m(String lon, String lat) {
        String location = LocationUtil.isValidLocation(lon, lat);
        if (StringUtils.isEmpty(location)){
            LOGGER.warn("lon or lat is invalid, lon is {}, lat is {}", lon, lat);
            return null;
        }

        // 调用cp接口获取数据
        String minutely5mWeatherString = HefengWeatherUtil.getMinutely5mWeather(location);
        WeatherResponse minutely5mWeather = JsonUtils.parseObject(minutely5mWeatherString, WeatherResponse.class);
        List<HefengMinutely> minutely = Optional.ofNullable(minutely5mWeather)
            .map(WeatherResponse::getMinutely)
            .orElse(null);
        if (minutely == null) {
            LOGGER.warn("get hefeng minute5m is failed, entity is {}", minutely5mWeatherString);
            return null;
        }
        return minutely;
    }

    public List<HefengWarning> getWarning(String cityCode) {
        String validCityCode = LocationUtil.isValidCityCode(cityCode);
        if (StringUtils.isEmpty(validCityCode)) {
            LOGGER.warn("cityCode is invalid, cityCode is {}", cityCode);
            return null;
        }

        // 从缓存中取值
        String key = HefengRedis.generateWarningKey(cityCode);
        Object cacheObject = RedisUtils.getCacheObject(key);
        if (cacheObject != null) {
            return (List<HefengWarning>) cacheObject;
        }

        // 调用cp接口获取数据
        String warningWeatherString = HefengWeatherUtil.getWarningWeather(cityCode);
        WeatherResponse warningWeather = JsonUtils.parseObject(warningWeatherString, WeatherResponse.class);
        List<HefengWarning> warnings = Optional.ofNullable(warningWeather)
            .map(WeatherResponse::getWarning)
            .orElse(null);
        if (warnings == null) {
            LOGGER.warn("get hefeng warning is failed, entity is {}", warningWeatherString);
            return null;
        }
        RedisUtils.setCacheObject(key, warnings, Duration.ofSeconds(HefengRedis.getWarningTime()));

        return warnings;
    }

    public HefengNow getNow(String lon , String lat) {
        String location = LocationUtil.isValidLocation(lon, lat);
        if (StringUtils.isEmpty(location)){
            LOGGER.warn("lon or lat is invalid, lon is {}, lat is {}", lon, lat);
            return null;
        }

        // 调用cp接口获取数据
        String nowWeatherString = HefengWeatherUtil.getNowWeather(location);
        WeatherResponse nowWeather = JsonUtils.parseObject(nowWeatherString, WeatherResponse.class);
        HefengNow now = Optional.ofNullable(nowWeather)
            .map(WeatherResponse::getNow)
            .orElse(null);
        if (now == null) {
            LOGGER.warn("get hefeng warning is failed, entity is {}", nowWeatherString);
            return null;
        }

        return now;

    }

    public List<HefengDaily> get3d(String cityCode) {
        String validCityCode = LocationUtil.isValidCityCode(cityCode);
        if (StringUtils.isEmpty(validCityCode)) {
            LOGGER.warn("cityCode is invalid, cityCode is {}", cityCode);
            return null;
        }

        // 从缓存中取值
        String key = HefengRedis.generate3dKey(cityCode);
        Object cacheObject = RedisUtils.getCacheObject(key);
        if (cacheObject != null) {
            return (List<HefengDaily>) cacheObject;
        }

        // 调用cp接口获取数据
        String forecast3dWeatherString = HefengWeatherUtil.getForecast3dWeather(cityCode);
        WeatherResponse forecast3dWeather = JsonUtils.parseObject(forecast3dWeatherString, WeatherResponse.class);
        List<HefengDaily> dailies = Optional.ofNullable(forecast3dWeather)
            .map(WeatherResponse::getDaily)
            .orElse(null);
        if (dailies == null) {
            LOGGER.warn("get hefeng 3d is failed, entity is {}", forecast3dWeatherString);
            return null;
        }
        RedisUtils.setCacheObject(key, dailies, Duration.ofSeconds(HefengRedis.getWarningTime()));

        return dailies;
    }
}
