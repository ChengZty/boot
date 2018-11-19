package com.test.controller;

import com.google.gson.Gson;
import com.heytea.boot.qiniuyun.QiNiuYunService;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 陈湘辉
 * @date 2018/8/29 下午8:34
 */
@Controller
public class QiNiuYunController {

    private final QiNiuYunService qiNiuYunService;

    @Autowired
    public QiNiuYunController(QiNiuYunService qiNiuYunService) {
        this.qiNiuYunService = qiNiuYunService;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "file is empty";
        }
        //原始名
         String originalFileName = file.getOriginalFilename();
        Response response = qiNiuYunService.uploadFile(file.getInputStream(), originalFileName);
         DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return "fileName : " + putRet.key;
    }

//    @RequestMapping("/")
//    public String index(){
//        return "index";
//    }
}