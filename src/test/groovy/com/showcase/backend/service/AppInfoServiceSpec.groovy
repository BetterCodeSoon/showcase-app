package com.showcase.backend.service

import com.showcase.backend.domain.AppInfo
import com.showcase.backend.error.ExceptionMessages
import com.showcase.backend.testhelper.BaseAppInfoUnitSpec
import jakarta.persistence.EntityNotFoundException

class AppInfoServiceSpec extends BaseAppInfoUnitSpec {

    def appInfoService = new AppInfoService(appInfoRepository)

    def "find returns entity when present"() {
        given: "Mock findById() with expected AppInfo"
        def expectedAppInfo = new AppInfo(version, desc)
        appInfoRepository.findById(expectedAppInfo.id) >> Optional.of(expectedAppInfo)

        expect: "Confirm expected is returned"
        appInfoService.find(expectedAppInfo.id) == expectedAppInfo
    }

    def "find throws EntityNotFoundException when not present"() {
        given:
        def notExistingId = 2L
        appInfoRepository.findById(notExistingId) >> Optional.empty()

        when:
        appInfoService.find(notExistingId)

        then:
        def ex = thrown(EntityNotFoundException)
        ex.message == String.format(ExceptionMessages.FIND_NO_ID, notExistingId)
    }
}