package com.chicplay.mediaserver.domain.individual_video.application;

import com.chicplay.mediaserver.domain.account.dao.AccountDao;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.dto.IndividualVideosGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IndividualVideoService {


    private final AccountDao accountDao;

    public List<IndividualVideosGetResponse> getListByUser(String id) {

        // user id를 통해 individualVideoList를 불러온다.
        List<IndividualVideo> individualVideoList = accountDao.findById(UUID.fromString(id)).getIndividualVideos();

        // response dto로 변환
        ArrayList<IndividualVideosGetResponse> individualVideosGetResponses = new ArrayList<>();
        individualVideoList.forEach(individualVideo -> {
            individualVideosGetResponses.add(IndividualVideosGetResponse.builder().individualVideo(individualVideo).build());
        });


        return individualVideosGetResponses;

    }
}
