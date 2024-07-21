package org.dromara.weather.utils;

import org.apache.commons.jexl3.*;

public class Jexl3Util {

    private static final JexlContext emptyContext=new MapContext();

    private static final JexlEngine jexlEngine = new JexlBuilder().create();

    public static boolean execute(String leftValue, String operator, String rightValue) {
        // 创建Jexl表达式解析器
        JexlExpression jexlExpression = jexlEngine.createExpression(leftValue +  operator + rightValue);

        // 执行Jexl表达式，得到结果
        Object execute = jexlExpression.evaluate(emptyContext);
        return (Boolean) execute;
    }
}
