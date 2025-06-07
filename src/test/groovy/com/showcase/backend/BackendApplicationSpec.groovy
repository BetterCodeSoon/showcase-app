package com.showcase.backend

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BackendApplicationSpec extends Specification {

    def "BackendApplication context load"() {
        expect:
        true
    }
}