package kr.co.hs.sandbox.domain

import kr.co.hs.sandbox.domain.entity.AndroidPlatformInfoEntity
import kr.co.hs.sandbox.domain.entity.PlatformInfoEntity

actual fun getPlatformInfoEntity(): PlatformInfoEntity = AndroidPlatformInfoEntity()