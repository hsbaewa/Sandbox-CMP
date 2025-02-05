package kr.co.hs.sandbox.domain.entity

import platform.UIKit.UIDevice

data class IOSPlatformInfoEntity(
    override val os: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
) : PlatformInfoEntity