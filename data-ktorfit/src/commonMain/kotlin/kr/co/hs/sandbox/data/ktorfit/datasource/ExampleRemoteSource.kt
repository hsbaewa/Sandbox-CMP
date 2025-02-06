package kr.co.hs.sandbox.data.ktorfit.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.data.ktorfit.dto.ExampleResponseDTO

internal interface ExampleRemoteSource {
    fun flowOfExampleResponse(): Flow<ExampleResponseDTO>
}