package org.dromara.common.utils.httpClient.config;

import org.dromara.common.utils.httpClient.HttpClientPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//开启配置
@EnableConfigurationProperties(HttpClientProperties.class)//开启使用映射实体对象
@ConditionalOnClass(HttpClientProperties.class)//存在HelloService时初始化该配置类
@ConditionalOnProperty//存在对应配置信息时初始化该配置类
    (
        prefix = "hello",//存在配置前缀hello
        value = "enabled",//开启
        matchIfMissing = true//缺失检查
    )
public class HttpClientAutoConfiguration {
    @Autowired
    private HttpClientProperties httpClientProperties;

    @Bean
    //容器中没有HttpClientPool实例时，初始化HttpClientPool并加入spring容器
    @ConditionalOnMissingBean(HttpClientPool.class)
    public HttpClientPool HttpClientPool() {
        System.out.println("HttpClientAutoConfiguration");
        return new HttpClientPool();
    }
}
