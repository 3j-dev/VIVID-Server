package com.chicplay.mediaserver.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static org.hibernate.boot.archive.internal.ArchiveHelper.getBytesFromInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.s3.test.video.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public void uploadFileToS3(String fileUrl) throws IOException {

        URL url = new URL(fileUrl);
        URLConnection conn =  url.openConnection();
        InputStream is = conn.getInputStream();

        byte[] f = IOUtils.toByteArray(is);
        ObjectMetadata metadata = new ObjectMetadata();

        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(f);
        metadata.setContentLength(f.length);

        // s3 upload
        amazonS3Client.putObject(new PutObjectRequest(bucket, "test.mp4", byteArrayIs, metadata).withCannedAcl(CannedAccessControlList.PublicRead));

    }
}
