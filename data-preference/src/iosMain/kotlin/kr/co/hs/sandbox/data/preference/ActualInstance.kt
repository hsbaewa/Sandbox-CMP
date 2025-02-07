package kr.co.hs.sandbox.data.preference

import kr.co.hs.sandbox.data.preference.datasource.IOSPreferenceDataSource
import kr.co.hs.sandbox.data.preference.datasource.PreferenceDataSource

internal actual fun getPreference(): PreferenceDataSource = IOSPreferenceDataSource()