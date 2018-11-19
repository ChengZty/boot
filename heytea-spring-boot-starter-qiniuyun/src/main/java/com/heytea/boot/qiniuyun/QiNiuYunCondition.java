package com.heytea.boot.qiniuyun;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * @author 陈湘辉
 * @date 2018/8/29 下午4:10
 */
@Slf4j
public class QiNiuYunCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String accessKey = conditionContext.getEnvironment().getProperty("qiniuyun.access-key");
        String secretKey = conditionContext.getEnvironment().getProperty("qiniuyun.secret-key");
        String bucket = conditionContext.getEnvironment().getProperty("qiniuyun.bucket");
        String cdnPrefix = conditionContext.getEnvironment().getProperty("qiniuyun.cdn-prefix");

        if (StringUtils.isEmpty(accessKey)) {
            log.error("【七牛云配置信息】未配置：qiniuyun.access-key");
        }else {
            log.info("【七牛云配置信息】：accessKey={}",accessKey);
        }
        if (StringUtils.isEmpty(secretKey)) {
            log.error("【七牛云配置信息】未配置：qiniuyun.secret-key");
        }else {
            log.info("【七牛云配置信息】：secretKey={}",secretKey);
        }
        if (StringUtils.isEmpty(bucket)) {
            log.error("【七牛云配置信息】未配置：qiniuyun.bucket");
        }else {
            log.info("【七牛云配置信息】：bucket={}",bucket);
        }

        return true;
    }
}
