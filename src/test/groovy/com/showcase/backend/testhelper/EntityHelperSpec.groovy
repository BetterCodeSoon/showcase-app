package com.showcase.backend.testhelper

import com.showcase.backend.domain.AppInfo
import spock.lang.Specification


class EntityHelperSpec extends Specification {

    public static final APPINFO_ID = 1L
    public static final APPINFO_VERSION = "0.0.1"
    public static final APPINFO_DESCRIPTION = "Tech demo with no functionality."

    public static final APPINFO_VERSION_2 = "0.0.2"
    public static final APPINFO_DESCRIPTION_2 = ""


    static AppInfo getAppInfo() {
        return AppInfo.builder()
                .version(APPINFO_VERSION)
                .description(APPINFO_DESCRIPTION)
                .build()
    }
}