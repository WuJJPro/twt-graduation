package com.graduation.component;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: 12613
 * @project: mooc
 * @pcakage: com.king.mooc.service.impl.QiniuServiceImpl
 * @date: 2022年05月02日 00:38
 * @description:
 */
@Service
public class QiniuPicture {
    @Value(value = "${Qiniu.AccessKey}")
    private String accessKey;
    @Value(value = "${Qiniu.SecretKey}")
    private String secretKey;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public String saveFile(MultipartFile uploadFile) {
        Configuration cfg = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(cfg);
        String bucket = "guangbojv";
        String fileName = uploadFile.getOriginalFilename();
        String key = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadFile.getBytes(), key, upToken);
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return "https://file.lingxiajidu.club/" + putRet.key;
        } catch (QiniuException e) {
            logger.error("上传文件失败", e);
            logger.error(JSON.toJSONString(e.response));
        } catch (IOException e) {
            logger.error("上传文件失败", e);
        }
        return null;
    }

    public List<String> saveFile(MultipartFile[] uploadFiles) {
        List<String> list = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles) {
            list.add(saveFile(uploadFile));
        }
        return list;
    }
}
