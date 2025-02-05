package kr.co.hs.sandbox.data

import kr.co.hs.sandbox.data.dto.PlatformDataDTO
import kr.co.hs.sandbox.domain.entity.PlatformInfoEntity
import kr.co.hs.sandbox.domain.entity.impl.AndroidPlatformInfoEntity
import kr.co.hs.sandbox.domain.entity.impl.IOSPlatformInfoEntity

internal object Mapper {
    fun PlatformDataDTO.toDomain(): PlatformInfoEntity {
        val osInfo = "${this.os.osName} ${this.versionName}"
        return when (this.os) {
            PlatformDataDTO.OS.Android -> AndroidPlatformInfoEntity(os = osInfo)
            PlatformDataDTO.OS.IOS -> IOSPlatformInfoEntity(os = osInfo)
        }
    }
}