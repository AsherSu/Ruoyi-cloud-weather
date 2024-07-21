package org.dromara.weather.domain.hefeng.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrecipitationMinutely {

    private List<Precipitation1m> data;

    public PrecipitationMinutely() {
        this.data = new ArrayList<>();
    }

    @Data
    public static class Precipitation1m {
        private String time;

        private String precipitation;

        public Precipitation1m(String time, String precipitation) {
            this.time = time;
            this.precipitation = precipitation;
        }
    }
}
