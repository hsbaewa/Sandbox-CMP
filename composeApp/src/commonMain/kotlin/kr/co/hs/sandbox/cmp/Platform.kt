package kr.co.hs.sandbox.cmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform