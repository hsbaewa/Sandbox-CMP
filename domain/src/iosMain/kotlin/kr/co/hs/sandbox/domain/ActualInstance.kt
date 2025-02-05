package kr.co.hs.sandbox.domain

import kr.co.hs.sandbox.domain.entity.IOSPlatformInfoEntity
import kr.co.hs.sandbox.domain.entity.PlatformInfoEntity

actual fun getPlatformInfoEntity(): PlatformInfoEntity = IOSPlatformInfoEntity()