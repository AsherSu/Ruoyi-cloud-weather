package org.dromara.weather.domain.vo;

import lombok.Data;
import org.dromara.weather.domain.rule.OneVariableExpression;

import java.util.List;

@Data
public class OneVariableRuleVO {

    String lon;

    String lat;

    String cityCode;

    String date;

    String operator;

    List<OneVariableExpression> expressions;
}
