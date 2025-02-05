package kr.co.hs.sandbox.data

import kr.co.hs.sandbox.domain.repository.PlatformInfoRepository

/**
 * expect 키워드 : multiplatform에서 사용되는 키워드로 추상화 개념인 abstract와 동일하나 android, iOS 에서 각각 구현을 강제하도록 하는 키워드
 */
expect fun getPlatformInfoRepository(): PlatformInfoRepository