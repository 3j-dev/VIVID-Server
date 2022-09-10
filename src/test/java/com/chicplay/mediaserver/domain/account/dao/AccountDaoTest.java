package com.chicplay.mediaserver.domain.account.dao;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.AccountBuilder;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo;
import com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideoBuilder;
import com.chicplay.mediaserver.domain.video.dao.VideoRepository;
import com.chicplay.mediaserver.domain.video.domain.Video;
import com.chicplay.mediaserver.domain.video.domain.VideoBuilder;
import com.chicplay.mediaserver.test.IntegrationTest;
import com.chicplay.mediaserver.test.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AccountDaoTest extends IntegrationTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    @DisplayName("[AccountDaoTest] account_find_by_id")
    public void account_find_by_id() {


    }


}