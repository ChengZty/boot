package com.heytea.boot.qiniuyun;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;

/**
 * @author 陈湘辉
 * @date 2018/8/29 下午4:17
 */
@Service
public class QiNiuYunServiceImpl implements QiNiuYunService {

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Autowired
    private QiNiuYunProperties qiNiuProperties;

    private StringMap putPolicy;

    @Override
    public Response uploadFile(File file, String name) throws QiniuException {
        Response response = this.uploadManager.put(file, name, getUploadToken(name));
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, name, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream, String name) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, name, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, name, getUploadToken(), null, null);
            retry++;
        }
        return response;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(qiNiuProperties.getBucket(), key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = bucketManager.delete(qiNiuProperties.getBucket(), key);
        }
        return response;
    }

    //会在依赖注入完成后执行
    @PostConstruct
    public void init() {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken(String fileName) {
        return this.auth.uploadToken(qiNiuProperties.getBucket(),fileName,3600,putPolicy);
    }
    private String getUploadToken() {
        return this.auth.uploadToken(qiNiuProperties.getBucket(),null,3600,putPolicy);
    }
}

