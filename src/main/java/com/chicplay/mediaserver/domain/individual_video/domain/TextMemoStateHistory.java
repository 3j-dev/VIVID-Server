package com.chicplay.mediaserver.domain.individual_video.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "text_memo_state_history")
public class TextMemoStateHistory extends TextMemoState{
}
