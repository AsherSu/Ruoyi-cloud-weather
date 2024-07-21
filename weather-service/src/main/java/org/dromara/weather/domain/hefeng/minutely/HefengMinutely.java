package org.dromara.weather.domain.hefeng.minutely;

import lombok.Data;

@Data
public class HefengMinutely {
    private String fxTime;
    private float precip;
    private String type;
}
