package org.dromara.weather.service.rule;

import cn.hutool.core.lang.Dict;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.weather.domain.common.ErrorCode;
import org.dromara.weather.domain.common.Result;
import org.dromara.weather.domain.hefeng.daily.HefengDaily;
import org.dromara.weather.domain.jexl3.*;
import org.dromara.weather.domain.rule.OneVariableExpression;
import org.dromara.weather.domain.vo.OneVariableRuleVO;
import org.dromara.weather.service.HefengWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class Forecast3dRuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Forecast3dRuleService.class);

    @Autowired
    private HefengWeatherService hefengWeatherService;

    public Result excute(OneVariableRuleVO oneVariableRuleVO) {
        List<OneVariableExpression> expressions = oneVariableRuleVO.getExpressions();
        if (expressions == null || expressions.isEmpty()) {
            LOGGER.warn("expressions is empty");
            return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
        }

        // 获取格点实时天气
        List<HefengDaily> hefengDailies = hefengWeatherService.get3d(oneVariableRuleVO.getCityCode());
        if (hefengDailies == null) {
            LOGGER.warn("get hefeng Dailies is failed,cityCode is {}",oneVariableRuleVO.getCityCode());
            return Result.failure(ErrorCode.HEFENG_CP_FAILED);
        }

        // 表达式校验 默认为与
        Expression expressionList = null;
        if ("and".equals(oneVariableRuleVO.getOperator())) {
            expressionList = new ExpressionAnd();
        } else if ("or".equals(oneVariableRuleVO.getOperator())) {
            expressionList = new ExpressionOr();
        } else {
            expressionList = new ExpressionAnd();
        }

        // 日期分类
        HashMap<String, Dict> dateMap = new HashMap<>();
        for (HefengDaily hefengDaily : hefengDailies){
            Dict dict = JsonUtils.parseMap(JsonUtils.toJsonString(hefengDaily));
            dateMap.put(hefengDaily.getFxDate(), dict);
        }
        if (!dateMap.containsKey(oneVariableRuleVO.getDate())){
            LOGGER.warn("date is not in 3d : {}", oneVariableRuleVO.getDate());
            return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
        }
        Dict dict = dateMap.get(oneVariableRuleVO.getDate());

        for (OneVariableExpression expression : expressions) {
            if (expression.getVariable() == null || StringUtils.isEmpty(expression.getVariable())) {
                LOGGER.warn("{} is inValid", expression);
                return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
            }
            String variable = (String) dict.get(expression.getVariable());
            if (StringUtils.isEmpty(variable)) {
                LOGGER.warn("{} is nonexistent", expression.getVariable());
                return Result.failure(expression.getVariable() + " is nonexistent");
            }
            expressionList.add(ComparisonExpression.generate(CalExpression.value(variable)
                , expression.getOperator(), CalExpression.value(expression.getValue())));
        }
        boolean execute = expressionList.execute();
        HashMap<String, Boolean> tmp = new HashMap<>();
        tmp.put("expressionResult", execute);
        return Result.success(tmp);
    }
}
