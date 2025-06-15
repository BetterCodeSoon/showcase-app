package com.showcase.backend.testhelper

import com.showcase.backend.repository.AppInfoRepository
import com.showcase.backend.util.SpringProfiles
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static com.showcase.backend.testhelper.EntityHelperSpec.APPINFO_DESCRIPTION
import static com.showcase.backend.testhelper.EntityHelperSpec.APPINFO_VERSION

@ActiveProfiles(SpringProfiles.TEST)
class BaseAppInfoUnitSpec extends Specification {

    final version = APPINFO_VERSION
    final desc = APPINFO_DESCRIPTION

    def appInfoRepository = Mock(AppInfoRepository);
}