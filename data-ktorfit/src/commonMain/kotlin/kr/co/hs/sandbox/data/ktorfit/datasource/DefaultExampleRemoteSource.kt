package kr.co.hs.sandbox.data.ktorfit.datasource

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kr.co.hs.sandbox.data.ktorfit.dto.ExampleResponseDTO

internal class DefaultExampleRemoteSource : ExampleRemoteSource {
    override fun flowOfExampleResponse(): Flow<ExampleResponseDTO> = flow {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
        client.plugin(HttpSend).intercept { request ->
            val originalCall = execute(request)
            if (originalCall.response.status.value !in 100..399) {
                execute(request)
            } else {
                originalCall
            }
        }

        val ktorfit = Ktorfit.Builder()
            .baseUrl("https://developers.google.com/")
            .httpClient(client)
            .build()
        val testApi = ktorfit.createExampleApi()
        emit(testApi.get())
    }
}