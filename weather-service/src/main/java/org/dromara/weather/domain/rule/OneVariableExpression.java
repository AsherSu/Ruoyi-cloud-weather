package org.dromara.weather.domain.rule;

import lombok.Data;

@Data
public class OneVariableExpression {
    private String variable;

    private String operator;

    private String value;
}
