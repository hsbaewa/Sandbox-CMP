package kr.co.hs.sandbox.data.preference

import kr.co.hs.sandbox.data.preference.datasource.AndroidPreferenceDataSource
import kr.co.hs.sandbox.data.preference.datasource.PreferenceDataSource

internal actual fun getPreference(): PreferenceDataSource = AndroidPreferenceDataSource()