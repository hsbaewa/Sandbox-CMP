package kr.co.hs.sandbox.data.ktorfit.datasource

import de.jensklingenberg.ktorfit.http.GET
import kr.co.hs.sandbox.data.ktorfit.dto.ExampleResponseDTO

internal interface ExampleApi {
    @GET("static/actions-center/verticals/things-to-do/reference/feed-spec/sample.json?hl=ko")
    suspend fun get(): ExampleResponseDTO
}