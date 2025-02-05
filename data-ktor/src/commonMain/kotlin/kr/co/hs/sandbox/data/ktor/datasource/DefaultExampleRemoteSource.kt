package kr.co.hs.sandbox.data.ktor.datasource

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kr.co.hs.sandbox.data.ktor.dto.ExampleResponseDTO

class DefaultExampleRemoteSource : ExampleRemoteSource {
    override fun flowOfExampleResponse(): Flow<ExampleResponseDTO> = flow {
        val client = HttpClient()
        client.plugin(HttpSend).intercept { request ->
            val originalCall = execute(request)
            if (originalCall.response.status.value !in 100..399) {
                execute(request)
            } else {
                originalCall
            }
        }

        val response =
            client.get("https://developers.google.com/static/actions-center/verticals/things-to-do/reference/feed-spec/sample.json?hl=ko")
        val text = response.bodyAsText()

        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
        emit(json.decodeFromString<ExampleResponseDTO>(text))
    }
}