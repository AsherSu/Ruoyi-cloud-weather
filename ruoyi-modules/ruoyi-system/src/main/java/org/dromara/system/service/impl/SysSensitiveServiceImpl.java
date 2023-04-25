package org.dromara.system.service.impl;

import org.dromara.common.core.service.SensitiveService;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

/**
 * 脱敏服务
 * 默认管理员不过滤
 * 需自行根据业务重写实现
 *
 * @author Lion Li
 * @version 3.6.0
 */
@Service
public class SysSensitiveServiceImpl implements SensitiveService {

    /**
     * 是否脱敏
     */
    @Override
    public boolean isSensitive() {
        return !LoginHelper.isSuperAdmin() || !LoginHelper.isTenantAdmin();
    }

}