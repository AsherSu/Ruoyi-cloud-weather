package org.dromara.weather.domain.jexl3;

import org.dromara.weather.utils.Jexl3Util;

public class ComparisonExpression implements Expression {

    private Operator operator;

    private CalExpression leftDigital;

    private CalExpression rightDigital;

    public ComparisonExpression(CalExpression leftValue, Operator operator, CalExpression rightValue) {
        this.operator = operator;
        this.leftDigital = leftValue;
        this.rightDigital = rightValue;
    }

    public static ComparisonExpression generate(CalExpression leftValue, String operator, CalExpression rightValue) {
        if (!CalExpression.isVaild(leftValue) || !CalExpression.isVaild(rightValue)){
            return null;
        }
        if ("gt".equals(operator)){
            return new ComparisonExpression(leftValue, Operator.GT, rightValue);
        }else if ("lt".equals(operator)){
            return new ComparisonExpression(leftValue, Operator.LT, rightValue);
        }else if ("ge".equals(operator)){
            return new ComparisonExpression(leftValue, Operator.GE, rightValue);
        }else if ("le".equals(operator)){
            return new ComparisonExpression(leftValue, Operator.LE, rightValue);
        }else if ("eq".equals(operator)){
            return new ComparisonExpression(leftValue, Operator.EQ, rightValue);
        }else {
            return null;
        }
    }

    public static ComparisonExpression gt(CalExpression leftValue, CalExpression rightValue) {
        return new ComparisonExpression(leftValue, Operator.GT, rightValue);
    }

    public static ComparisonExpression lt(CalExpression leftValue, CalExpression rightValue) {
        return new ComparisonExpression(leftValue, Operator.LT, rightValue);
    }

    public static ComparisonExpression eq(CalExpression leftValue, CalExpression rightValue) {
        return new ComparisonExpression(leftValue, Operator.EQ, rightValue);
    }

    public static ComparisonExpression ge(CalExpression leftValue, CalExpression rightValue) {
        return new ComparisonExpression(leftValue, Operator.GE, rightValue);
    }

    public static ComparisonExpression le(CalExpression leftValue, CalExpression rightValue) {
        return new ComparisonExpression(leftValue, Operator.LE, rightValue);
    }

    @Override
    public boolean execute() {
        return Jexl3Util.execute(leftDigital.calculate(), operator.get(), rightDigital.calculate());
    }

    public enum Operator {
        GT(">"),
        LT("<"),
        EQ("=="),
        GE(">="),
        LE("<=");

        private String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        public String get() {
            return operator;
        }
    }
}
