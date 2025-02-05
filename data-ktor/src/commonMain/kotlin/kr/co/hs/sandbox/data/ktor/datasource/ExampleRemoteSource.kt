package kr.co.hs.sandbox.data.ktor.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.data.ktor.dto.ExampleResponseDTO

interface ExampleRemoteSource {
    fun flowOfExampleResponse(): Flow<ExampleResponseDTO>
}