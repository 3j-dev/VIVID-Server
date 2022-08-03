package com.chicplay.mediaserver.domain.video.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.chicplay.mediaserver.domain.video.dto.UploadSnapshotImageResponse;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<UploadSnapshotImageResponse> uploadSnapshotImagesToS3(List<MultipartFile> multipartFile, String classParticipantListId){

        List<UploadSnapshotImageResponse> fileUrlList = new ArrayList<>();

        multipartFile.forEach(file -> {

            // test용, 실제는 매핑 필요.
            String folderName = UUID.randomUUID().toString();

            // fileName은 캡처 시간대
            String fileName = UUID.randomUUID().toString();
            //String fileName = file.getName();

            String objectKey = folderName + '/' + fileName;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(imageSnapshotBucket, objectKey , inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                throw new ImageUploadFailedException();
            }

            fileUrlList.add(UploadSnapshotImageResponse.builder()
                    .filePath(String.valueOf(amazonS3Client.getUrl(imageSnapshotBucket,objectKey)))
                    .time(LocalTime.of(00,1,22,00)).build());

        });

        return fileUrlList;
    }

}
