package kr.co.hs.sandbox.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun flowOfButtonClickCount(count: Int): Flow<Boolean>
    fun flowOfButtonClickCount(): Flow<Int>
}