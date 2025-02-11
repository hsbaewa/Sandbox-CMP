package kr.co.hs.sandbox.presentation

import kotlinx.serialization.Serializable

sealed class Presentation {
    @Serializable
    data object Main : Presentation()

    @Serializable
    class DateTimeSample(val millis: Long) : Presentation()

    @Serializable
    data object PreferenceSample : Presentation()

    @Serializable
    data object DatabaseSample : Presentation()

    @Serializable
    class OpenWebLinkSample(val url: String) : Presentation()
}

