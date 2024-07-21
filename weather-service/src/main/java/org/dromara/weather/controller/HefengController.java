package org.dromara.weather.controller;

import org.dromara.weather.domain.common.ErrorCode;
import org.dromara.weather.domain.common.Result;
import org.dromara.weather.domain.vo.OneVariableRuleVO;
import org.dromara.weather.service.rule.Forecast3dRuleService;
import org.dromara.weather.service.rule.NowRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hefeng/precipitation")
public class HefengController {
    @Autowired
    private NowRuleService nowRuleService;

    @Autowired
    private Forecast3dRuleService forecast3dRuleService;

    @PostMapping("/rule/now")
    public Result ruleNow(@RequestBody OneVariableRuleVO oneVariableRuleVO) {
        if (oneVariableRuleVO.getLon().isBlank() || oneVariableRuleVO.getLat().isBlank()){
            return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
        }
        if (oneVariableRuleVO.getExpressions().isEmpty()){
            return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
        }
        return nowRuleService.excute(oneVariableRuleVO);
    }

    @PostMapping("/rule/3d")
    public Result rule3d(@RequestBody OneVariableRuleVO oneVariableRuleVO) {
        if (oneVariableRuleVO.getCityCode().isBlank()){
            return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
        }
        if (oneVariableRuleVO.getExpressions().isEmpty()){
            return Result.failure(ErrorCode.PARAMETER_VALIDATE_ERROR);
        }
        return forecast3dRuleService.excute(oneVariableRuleVO);
    }

}
