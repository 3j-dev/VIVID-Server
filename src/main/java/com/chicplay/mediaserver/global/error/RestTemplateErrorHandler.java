package com.chicplay.mediaserver.global.error;

import com.chicplay.mediaserver.global.error.exception.BusinessException;
import com.chicplay.mediaserver.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        final HttpStatus statusCode = response.getStatusCode();

        // 상태코드 2xx가 아닐 경우 true 반환
        return !statusCode.is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        log.info(response.getStatusCode().toString());
        log.info(response.getStatusText());
        log.info(response.getBody().toString());

        throw new BusinessException(ErrorCode.EXTERNAL_API_FAILED);

    }
}
