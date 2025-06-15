package com.showcase.backend.integration.controller

import com.showcase.backend.controller.AppInfoController
import com.showcase.backend.integration.BaseIntegrationSpec
import com.showcase.backend.service.AppInfoService
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static com.showcase.backend.testhelper.EntityHelperSpec.APPINFO_DESCRIPTION
import static com.showcase.backend.testhelper.EntityHelperSpec.APPINFO_VERSION
import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class ITAppInfoControllerSpec extends BaseIntegrationSpec {

    @Autowired
    MockMvc mockMvc

    @Autowired
    AppInfoService appInfoService;

    def setup() {
        clearDatabase()
    }

    def "should create, retrieve, update and delete an AppInfo via REST API"() {
        given: "a new AppInfo JSON"
        def appInfoJson = JsonOutput.toJson([
                version    : APPINFO_VERSION,
                description: APPINFO_DESCRIPTION
        ])

        and: "the url to the AppInfo service"
        def url = "${AppInfoController.ENDPOINT_URL}"

        when: "send a POST request to save the AppInfo over the endpoint"
        def saveResult = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(appInfoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').exists())
                .andExpect(jsonPath('$.version').value(APPINFO_VERSION))
                .andExpect(jsonPath('$.description').value(APPINFO_DESCRIPTION))
                .andReturn()

        and: "retrieve the id from the response object"
        Long idFromSaveResult = new JsonSlurper().parseText(saveResult.response.contentAsString).id

        then: "check in database and confirm AppInfo table has one and only one entry"
        appInfoService.count() == 1

        when: "retrieve AppInfo from database with id from response"
        def databaseEntity = appInfoService.find(idFromSaveResult)

        then: "compare the database AppInfo with expected properties"
        databaseEntity.version == APPINFO_VERSION
        databaseEntity.description == APPINFO_DESCRIPTION

        when: "send a GET request to retrieve all AppInfo entities and confirm the list size is one"
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(1)))
                .andExpect(jsonPath('$[0].id').value(idFromSaveResult))

        and: "construct endpoint url with id"
        def urlWithId = "$url/$idFromSaveResult"

        and: "send a GET request to retrieve the AppInfo over endpoint and confirm values"
        mockMvc.perform(get(urlWithId))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(idFromSaveResult))
                .andExpect(jsonPath('$.version').value(APPINFO_VERSION))
                .andExpect(jsonPath('$.description').value(APPINFO_DESCRIPTION))


        and: "create json for an updated version of the AppInfo"
        def expectedVersion = "1234"
        def expectedDesc = "Some new text"
        def updatedAppInfoJson = JsonOutput.toJson([
                version    : expectedVersion,
                description: expectedDesc
        ])

        and: "send a PUT request with the updated AppInfo Json via endpoint"
        mockMvc.perform(put(urlWithId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedAppInfoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.version').value(expectedVersion))
                .andExpect(jsonPath('$.description').value(expectedDesc))

        and: "retrieve AppInfo from database"
        databaseEntity = appInfoService.find(idFromSaveResult)

        then: "database should still only have one entry"
        appInfoService.count() == 1

        and: "database object should have been updated"
        databaseEntity.version == expectedVersion
        databaseEntity.description == expectedDesc

        when: "send a DELETE request with id via endpoint"
        mockMvc.perform(delete(urlWithId))
                .andExpect(status().isOk())

        then: "database should be empty"
        appInfoService.count() == 0

        and: "the GET request for entity fails"
        mockMvc.perform(get(urlWithId))
                .andExpect(status().isNotFound())
    }
}