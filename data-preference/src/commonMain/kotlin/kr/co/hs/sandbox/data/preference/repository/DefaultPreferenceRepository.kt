package kr.co.hs.sandbox.data.preference.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.hs.sandbox.data.preference.datasource.PreferenceDataSource
import kr.co.hs.sandbox.domain.repository.PreferenceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DefaultPreferenceRepository : PreferenceRepository, KoinComponent {

    private val dataSource: PreferenceDataSource = get()

    override fun flowOfButtonClickCount(count: Int): Flow<Boolean> = dataSource
        .flowOfEdit(PREFERENCE_KEY_CLICK_COUNT, count)
        .map { true }

    override fun flowOfButtonClickCount(): Flow<Int> = dataSource
        .flowOfData(PREFERENCE_KEY_CLICK_COUNT, 0)

    companion object {
        const val PREFERENCE_KEY_CLICK_COUNT = "PREFERENCE_KEY_CLICK_COUNT"
    }
}