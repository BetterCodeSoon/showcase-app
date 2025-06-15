package com.showcase.backend.integration.service

import com.showcase.backend.integration.BaseIntegrationSpec
import com.showcase.backend.service.AppInfoService
import org.springframework.beans.factory.annotation.Autowired

class ITAppInfoServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private AppInfoService appInfoService;

}