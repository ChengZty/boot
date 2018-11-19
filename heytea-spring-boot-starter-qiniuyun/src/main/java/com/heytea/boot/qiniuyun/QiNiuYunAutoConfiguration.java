package com.heytea.boot.qiniuyun;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈湘辉
 * @date 2018/8/29 下午4:01
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(QiNiuYunProperties.class)
@ConditionalOnClass(QiNiuYunService.class)
@Conditional(QiNiuYunCondition.class)
@ConditionalOnProperty(prefix = "qiniuyun",value = "enabled",matchIfMissing = true)
public class QiNiuYunAutoConfiguration {

    private final QiNiuYunProperties qiNiuYunProperties;

    @Autowired
    public QiNiuYunAutoConfiguration(QiNiuYunProperties qiNiuYunProperties) {
        this.qiNiuYunProperties = qiNiuYunProperties;
    }

    @Bean
    @ConditionalOnMissingBean(QiNiuYunServiceImpl.class)
    public QiNiuYunService qiNiuYunService() {
        return new QiNiuYunServiceImpl();
    }

    /**
     * 华东   Zone.zone0()
     * 华北   Zone.zone1()
     * 华南   Zone.zone2()
     * 北美   Zone.zoneNa0()
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    /**
     * 构建一个七牛上传工具实例
     *
     * @return
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     *
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(qiNiuYunProperties.getAccessKey(), qiNiuYunProperties.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     *
     * @return
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }

    /**
     * 配置gson为json解析工具
     *
     * @return
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }

}
