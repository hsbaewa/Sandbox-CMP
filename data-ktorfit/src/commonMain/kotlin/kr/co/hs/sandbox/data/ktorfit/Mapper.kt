package kr.co.hs.sandbox.data.ktorfit

import kr.co.hs.sandbox.data.ktorfit.dto.ExampleResponseDTO
import kr.co.hs.sandbox.domain.entity.RemoteInfoEntity

internal object Mapper {
    fun ExampleResponseDTO.toDomain(): RemoteInfoEntity {
        return object : RemoteInfoEntity {
            override val text: String = this@toDomain.metadata.processing
        }
    }
}