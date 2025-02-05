package kr.co.hs.sandbox.domain.entity

import android.os.Build

data class AndroidPlatformInfoEntity(
    override val os: String = "Android ${Build.VERSION.RELEASE}"
) : PlatformInfoEntity