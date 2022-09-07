package com.chicplay.mediaserver.domain.video.domain;

import com.chicplay.mediaserver.domain.account.domain.Account;

import static org.junit.jupiter.api.Assertions.*;

public class VideoBuilder {

    public static String VIDEO_FILE_PATH = "test.aws.com";
    public static String VIDEO_UPLOADER_ID = "UUID-TEST-1111";
    public static String VIDEO_CHATTING_JSON_FILE_PATH = "test.aws.com";
    public static String VIDEO_INDEXING_IMAGE_FILE_PATH = "test.aws.com";


    public static Video build(){

        Video video = Video.builder()
                .filePath(VIDEO_FILE_PATH)
                .uploaderId(VIDEO_UPLOADER_ID)
                .chattingJsonFilePath(VIDEO_CHATTING_JSON_FILE_PATH)
                .videoIndexingImageFilePath(VIDEO_INDEXING_IMAGE_FILE_PATH)
                .build();
        return video;
    }

    public static Video build(Account account){

        Video video = Video.builder()
                .filePath(VIDEO_FILE_PATH)
                .uploaderId(account.getEmail())
                .chattingJsonFilePath(VIDEO_CHATTING_JSON_FILE_PATH)
                .videoIndexingImageFilePath(VIDEO_INDEXING_IMAGE_FILE_PATH)
                .build();
        return video;
    }

    public static Video build(String filePath, String uploaderId, String chattingJsonFilePath, String indexingImageFilePath){

        Video video = Video.builder()
                .filePath(filePath).uploaderId(uploaderId)
                .chattingJsonFilePath(chattingJsonFilePath).videoIndexingImageFilePath(indexingImageFilePath)
                .build();

        return video;
    }

}