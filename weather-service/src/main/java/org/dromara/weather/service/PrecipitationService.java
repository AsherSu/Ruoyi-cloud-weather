package org.dromara.weather.service;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.SimpleCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.weather.domain.common.ErrorCode;
import org.dromara.weather.domain.common.Result;
import org.dromara.weather.domain.hefeng.common.PrecipitationMinutely;
import org.dromara.weather.domain.hefeng.common.WeatherResponse;
import org.dromara.weather.domain.hefeng.minutely.HefengMinutely;
import org.dromara.weather.utils.HefengWeatherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PrecipitationService {

    @Autowired
    private HefengWeatherService hefengWeatherService;

    public void fly(String Location, String deviceType) {

        // 根据设备取最大承受降水量
//        String maxPrecipitation = "100";
//
//        List<HefengMinutely> minutely5m = hefengWeatherService.getMinutely5m(Location);
//        if (minutely5m == null) {
//            return;
//        }
//
//        CalResult calResult = new CalResult();
//
//        minutely5m.stream()
//            .filter(minutely -> minutely.getPrecip() > Double.parseDouble(maxPrecipitation))
//            .findFirst()
//        minutely5m.stream()
//            .filter(minutely -> minutely.getPrecip() > Double.parseDouble(maxPrecipitation))
//            .findFirst()
//            .ifPresentOrElse(
//                minutely -> {
//                    Instant end = Instant.parse(minutely.getFxTime());
//                    Instant start = Instant.now();
//                    long minutes = Duration.between(start, end).toMinutes();
//                    calResult.setContent(minutes + "分钟后降雨量超出无人机承受能力");
//                },
//                () -> {
//                    minutely5m.stream()
//                        .filter(minutely -> minutely.getPrecip() > 0)
//                        .findFirst()
//                        .ifPresentOrElse(
//                            minutely -> {
//                                Instant end = Instant.parse(minutely.getFxTime());
//                                Instant start = Instant.now();
//                                long minutes = Duration.between(start, end).toMinutes();
//                                calResult.setContent(minutes + "分钟后开始降雨");
//                            },
//                            () -> {
//                                calResult.setContent("两小时内无降雨");
//                            });
//                });
//


    }

    public Result<PrecipitationMinutely> getPrecipitationMinutely(String location, int minute) {

        String minutely5mWeatherString = HefengWeatherUtil.getMinutely5mWeather(location);
        WeatherResponse minutely5mWeather = JsonUtils.parseObject(minutely5mWeatherString, WeatherResponse.class);
        List<HefengMinutely> minutely = Optional.of(minutely5mWeather)
            .map(WeatherResponse::getMinutely)
            .orElse(null);
        if (minutely == null){
            return Result.failure(ErrorCode.SERVER_ERROR);
        }

        double[] fitting = fitting(location, minutely);
        double[] precipitation = precipitation(minute, fitting);
        PrecipitationMinutely precipitationMinutely = new PrecipitationMinutely();
        String startTime = minutely.get(0).getFxTime();
        LocalDateTime dateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm+08:00"));

        for (int i = 0; i < minute; i++) {
            precipitationMinutely.getData().add(new PrecipitationMinutely.Precipitation1m(dateTime.plusMinutes(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),String.valueOf(precipitation[i])));
        }
        return Result.success(precipitationMinutely);
    }

    public static double[] fitting(String location, List<HefengMinutely> minutely) {
        ParametricUnivariateFunction function = new PolynomialFunction.Parametric();
        // 初始化拟合
        double[] guess = new double[20];
        SimpleCurveFitter curveFitter = SimpleCurveFitter.create(function, guess);

        // 添加数据点
        WeightedObservedPoints observedPoints = new WeightedObservedPoints();

        for (int i = 0; i < minutely.size(); i++) {
            observedPoints.add(i, minutely.get(i).getPrecip());
        }

        return curveFitter.fit(observedPoints.toList());
    }

    private static double[] precipitation(int minute, double[] best) {
        double[] doubles = new double[minute];
        for (int x = 0; x < minute; x++) {
            double y = best[0] + best[1] * x + best[2] * Math.pow(x, 2) + best[3] * Math.pow(x, 3)
                + best[4] * Math.pow(x, 4) + best[5] * Math.pow(x, 5) + best[6] * Math.pow(x, 6)
                + best[7] * Math.pow(x, 7) + best[8] * Math.pow(x, 8) + best[9] * Math.pow(x, 9)
                + best[10] * Math.pow(x, 10) + best[11] * Math.pow(x, 11) + best[12] * Math.pow(x, 12)
                + best[13] * Math.pow(x, 13) + best[14] * Math.pow(x, 14) + best[15] * Math.pow(x, 15)
                + best[16] * Math.pow(x, 16) + best[17] * Math.pow(x, 17) + best[18] * Math.pow(x, 18)
                + best[19] * Math.pow(x, 19);
            doubles[x] = y;
        }
        return doubles;
    }
}
