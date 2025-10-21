package com.sky.utils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.sky.properties.AliOssProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Slf4j
public class AliOssUtil {

    private final String endpoint;
    private final String accessKeyId;
    private final String accessKeySecret;
    private final String bucketName;

    public AliOssUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    public String upload(byte[] bytes, String originalFilename) {
        log.info("开始上传文件到OSS，Bucket：{}", bucketName);

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            String fileName = UUID.randomUUID().toString();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = fileName + suffix;

            ObjectMetadata metadata = new ObjectMetadata();
            if (suffix.equalsIgnoreCase(".jpg") || suffix.equalsIgnoreCase(".jpeg")) {
                metadata.setContentType("image/jpeg");
            } else if (suffix.equalsIgnoreCase(".png")) {
                metadata.setContentType("image/png");
            } else if (suffix.equalsIgnoreCase(".gif")) {
                metadata.setContentType("image/gif");
            } else {
                metadata.setContentType("application/octet-stream");
            }

            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes), metadata);

            String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
            log.info("文件上传成功，访问URL：{}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("OSS文件上传失败", e);
            throw new RuntimeException("文件上传至OSS失败", e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
                log.info("OSS客户端已关闭");
            }
        }
    }
}
