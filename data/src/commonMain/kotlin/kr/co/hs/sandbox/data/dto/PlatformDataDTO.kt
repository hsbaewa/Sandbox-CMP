package kr.co.hs.sandbox.data.dto

data class PlatformDataDTO(
    val os: OS,
    val versionName: String
) {
    enum class OS(val osName: String) { Android("Android"), IOS("iOS") }
}