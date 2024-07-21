package org.dromara.weather.domain.hefeng.daily;

import lombok.Data;

@Data
public class HefengDaily {
    // 日期 2021-11-15
    private String fxDate;

    // 日出时间
    private String sunrise;

    // 日落时间
    private String sunset;

    // 月升时间
    private String moonrise;

    // 月落时间
    private String moonset;
    private String moonPhase;
    private String moonPhaseIcon;
    private String tempMax;
    private String tempMin;
    private String iconDay;
    private String textDay;
    private String iconNight;
    private String textNight;
    private String wind360Day;
    private String windDirDay;
    private String windScaleDay;
    private String windSpeedDay;
    private String wind360Night;
    private String windDirNight;
    private String windScaleNight;
    private String windSpeedNight;
    private String humidity;
    private String precip;
    private String pressure;
    private String vis;
    private String cloud;
    private String uvIndex;
}
