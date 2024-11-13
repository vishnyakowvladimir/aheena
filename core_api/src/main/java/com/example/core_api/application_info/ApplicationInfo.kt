package com.example.core_api.application_info

import javax.inject.Inject

class ApplicationInfo @Inject constructor(
    val applicationId: String,
    val buildType: String,
    val versionCode: Int,
    val versionName: String,
) {

    val isDebug: Boolean get() = buildType == "debug"

    val isRelease: Boolean get() = buildType == "release"
}
