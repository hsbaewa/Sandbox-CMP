package kr.co.hs.sandbox.data

import kr.co.hs.sandbox.data.repository.AndroidInfoRepository
import kr.co.hs.sandbox.domain.repository.PlatformInfoRepository

/**
 * actual 키워드 : expect 로 정의한 함수를 구현할때 쓰는 키워드
 */
actual fun getPlatformInfoRepository(): PlatformInfoRepository = AndroidInfoRepository()