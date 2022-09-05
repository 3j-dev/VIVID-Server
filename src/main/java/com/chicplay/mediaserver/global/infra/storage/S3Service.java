package com.chicplay.mediaserver.global.infra.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.chicplay.mediaserver.domain.individual_video.dto.SnapshotImageUploadResponse;
import com.chicplay.mediaserver.domain.video.exception.ImageUploadFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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
    private String rawVideoBucket;

    @Value("${cloud.aws.s3.image.snapshot.bucket}")
    private String imageSnapshotBucket;


    private final AmazonS3Client amazonS3Client;

    public void uploadRawVideoToS3(String fileUrl) throws IOException {

        URL url = new URL(fileUrl);
        URLConnection conn =  url.openConnection();
        InputStream is = conn.getInputStream();

        byte[] f = IOUtils.toByteArray(is);
        ObjectMetadata metadata = new ObjectMetadata();

        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(f);
        metadata.setContentLength(f.length);

        // s3 upload
        amazonS3Client.putObject(new PutObjectRequest(rawVideoBucket, "test.mp4", byteArrayIs, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    // 스냅샷 이미지를 s3에 업로드하는 메소드
    public SnapshotImageUploadResponse uploadSnapshotImagesToS3(MultipartFile file, String individualVideoId, String videoTime){

        // 디렉토리 이름은 individualVideoId, fileName은 캡처 시간대
        String objectKey = individualVideoId + '/' + videoTime;

        // 메타 데이터 추출
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        // s3 upload
        try(InputStream inputStream = file.getInputStream()) {
             amazonS3Client.putObject(new PutObjectRequest(imageSnapshotBucket, objectKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ImageUploadFailedException();
        }

        SnapshotImageUploadResponse response = SnapshotImageUploadResponse.builder()
                .filePath(String.valueOf(amazonS3Client.getUrl(imageSnapshotBucket, objectKey)))
                .time(videoTime).build();

        return response;
    }

}
