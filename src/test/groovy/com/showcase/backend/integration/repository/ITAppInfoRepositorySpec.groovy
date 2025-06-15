package com.showcase.backend.integration.repository


import com.showcase.backend.repository.AppInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import static com.showcase.backend.testhelper.AssertionHelperSpec.assertSameProperties
import static com.showcase.backend.testhelper.EntityHelperSpec.getAppInfo

@DataJpaTest
class ITAppInfoRepositorySpec extends Specification {

    @Autowired
    AppInfoRepository appInfoRepository;

    def "should save, retrieve and delete an AppInfo entity"() {
        given: "given an AppInfo entity"
        def expectedEntity = getAppInfo()

        when:
        "$expectedEntity object getting saved"
        def savedEntity = appInfoRepository.save(expectedEntity)

        then: "find entity by id"
        def foundEntity = appInfoRepository.findById(savedEntity.id).orElse(null)

        and: "found entity is not null"
        foundEntity != null

        and: "found entity equals saved entity"
        assertSameProperties(foundEntity, expectedEntity)

        when: "delete the entity"
        appInfoRepository.deleteById(foundEntity.id)

        then: "the entity cannot be found anymore"
        !appInfoRepository.findById(expectedEntity.id).isPresent()

        and: "the repository is empty"
        appInfoRepository.count() == 0
    }
}