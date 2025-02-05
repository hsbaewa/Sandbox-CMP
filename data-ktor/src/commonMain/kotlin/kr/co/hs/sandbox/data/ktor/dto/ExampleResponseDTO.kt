package kr.co.hs.sandbox.data.ktor.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExampleResponseDTO(
    @SerialName("feed_metadata") val metadata: Metadata
) {
    @Serializable
    data class Metadata(
        @SerialName("shard_id") val shardId: Int,
        @SerialName("total_shards_count") val count: Int,
        @SerialName("processing_instruction") val processing: String,
        val nonce: Long
    )
}