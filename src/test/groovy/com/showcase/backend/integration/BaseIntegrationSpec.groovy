package com.showcase.backend.integration

import com.showcase.backend.BackendApplication
import com.showcase.backend.repository.AppInfoRepository
import com.showcase.backend.util.SpringProfiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = BackendApplication)
@ActiveProfiles(SpringProfiles.INTEGRATION)
class BaseIntegrationSpec extends Specification {

    @Autowired
    AppInfoRepository appInfoRepository;

    def "confirm database is empty"() {
        expect:
        appInfoRepository.count() == 0
    }

    def clearDatabase() {
        appInfoRepository.deleteAll()
    }

    def cleanup() {
        clearDatabase()
    }
}