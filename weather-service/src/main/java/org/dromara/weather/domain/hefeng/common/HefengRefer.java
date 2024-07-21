package org.dromara.weather.domain.hefeng.common;

import lombok.Data;

import java.util.List;

@Data
public class HefengRefer {
    private List<String> sources;
    private List<String> license;
}
