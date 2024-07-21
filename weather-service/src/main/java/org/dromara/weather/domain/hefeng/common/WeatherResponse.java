package org.dromara.weather.domain.hefeng.common;

import lombok.Data;
import org.dromara.weather.domain.hefeng.daily.HefengDaily;
import org.dromara.weather.domain.hefeng.hourly.HefengHourly;
import org.dromara.weather.domain.hefeng.minutely.HefengMinutely;
import org.dromara.weather.domain.hefeng.now.HefengNow;
import org.dromara.weather.domain.hefeng.warning.HefengWarning;

import java.util.List;

@Data
public class WeatherResponse {
    private String code;
    private String updateTime;
    private String fxLink;
    private HefengNow now;
    private List<HefengDaily> daily;
    private List<HefengHourly> hourly;
    private List<HefengMinutely> minutely;
    private List<HefengWarning> warning;
    private HefengRefer refer;
}
