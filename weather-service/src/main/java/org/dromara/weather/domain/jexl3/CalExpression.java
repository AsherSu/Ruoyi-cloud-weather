package org.dromara.weather.domain.jexl3;

import lombok.Getter;
import lombok.Setter;
import org.dromara.common.core.utils.StringUtils;

@Getter
@Setter
public class CalExpression {
    private String leftValue;

    private Operator operator;

    private String rightValue;

    public CalExpression(String leftValue, Operator operator, String rightValue) {
        this.leftValue = leftValue;
        this.operator = operator;
        this.rightValue = rightValue;
    }

    public CalExpression(String value) {
        leftValue = value;
        operator = Operator.ADD;
        rightValue = "0";
    }

    public static boolean isVaild(CalExpression calExpression) {
        if (calExpression == null) {
            return false;
        }
        if (StringUtils.isEmpty(calExpression.leftValue) &&
            StringUtils.isEmpty(calExpression.rightValue)) {
            return false;
        }
        return true;
    }

    public static CalExpression value(String value) {
        return new CalExpression(value);
    }

    public static CalExpression add(String leftValue, String rightValue) {
        return new CalExpression(leftValue, Operator.ADD, rightValue);
    }

    public static CalExpression sub(String leftValue, String rightValue) {
        return new CalExpression(leftValue, Operator.SUB, rightValue);
    }

    public static CalExpression mul(String leftValue, String rightValue) {
        return new CalExpression(leftValue, Operator.MUL, rightValue);
    }

    public static CalExpression div(String leftValue, String rightValue) {
        return new CalExpression(leftValue, Operator.DIV, rightValue);
    }

    public static CalExpression mod(String leftValue, String rightValue) {
        return new CalExpression(leftValue, Operator.MOD, rightValue);
    }

    public String calculate() {
        return "(" + leftValue + operator.get() + rightValue + ")";
    }

    public enum Operator {
        ADD("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        MOD("%");
        private String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        public String get() {
            return operator;
        }
    }
}
