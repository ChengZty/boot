package com.heytea.boot.qiniuyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 陈湘辉
 * @date 2018/8/29 下午6:26
 */
@Data
@ConfigurationProperties(prefix = "qiniuyun")
public class QiNiuYunProperties {

    /** 七牛云的密钥.*/
    private String accessKey = "your_accessKey";

    private String secretKey = "your_secretKey";

    /** 存储空间名字.*/
    private String bucket = "your_bucket";

    /** 一般设置为cdn.*/
    private String cdnPrefix = "cdn";
}

