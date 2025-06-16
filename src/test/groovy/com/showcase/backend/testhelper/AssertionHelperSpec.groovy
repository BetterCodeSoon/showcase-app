package com.showcase.backend.testhelper

import com.showcase.backend.domain.AppInfo
import spock.lang.Specification
import spock.lang.Unroll

import static com.showcase.backend.testhelper.EntityHelperSpec.*

class AssertionHelperSpec extends Specification {

    static def getClassProperties(Object obj) {
        obj.properties.findAll {
            property, _ -> !(property as String in ['class', 'metaClass'])
        }
    }

    static boolean assertSameProperties(Object source, Object target) {
        assert source.class == target.class: "Object $source.class is not the same class as $target.class"
        def sourceProperties = getClassProperties(source)
        def targetProperties = getClassProperties(target)
        assert sourceProperties == targetProperties: "Object Properties differ: $sourceProperties vs $targetProperties"
        return true
    }

    def "assertSameProperties() returns true for two objects with same properties"() {
        given:
        def appInfo = new AppInfo(APPINFO_ID, APPINFO_VERSION, APPINFO_DESCRIPTION)
        def expected = new AppInfo(APPINFO_ID, APPINFO_VERSION, APPINFO_DESCRIPTION)

        expect:
        assertSameProperties(appInfo, expected)
    }

    @Unroll
    def "assertSameProperties should return AssertionError for #testCase"() {
        given:
        def expected = getAppInfo()

        when:
        assertSameProperties(actual, expected)

        then:
        thrown(expectedException)

        where:
        testCase            | actual                                                | expectedException
        "Wrong ID"          | new AppInfo(5L, APPINFO_VERSION, APPINFO_DESCRIPTION) | AssertionError
        "Wrong version"     | new AppInfo(APPINFO_ID, "wrong", APPINFO_DESCRIPTION) | AssertionError
        "Wrong description" | new AppInfo(APPINFO_ID, APPINFO_VERSION, "wrong")     | AssertionError
    }
}