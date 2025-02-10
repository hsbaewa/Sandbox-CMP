package kr.co.hs.sandbox.cmp

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DateTimeTest {
    @Test
    fun do_test_system_time() {
        val currentTimeInstant = Clock.System.now()
        assertTrue(currentTimeInstant.toEpochMilliseconds() > 0)

        val timeZone = TimeZone.currentSystemDefault()

        val localTimeDateTime = currentTimeInstant.toLocalDateTime(timeZone)
        val year = localTimeDateTime.year
        assertTrue(year > 0)

        val toInstant = localTimeDateTime.toInstant(timeZone)
        assertEquals(currentTimeInstant.toEpochMilliseconds(), toInstant.toEpochMilliseconds())
    }

    @Test
    fun do_test_date_format() {
        val format = DateTimeComponents.Format {
            year()
            chars("-")
            monthNumber()
            chars("-")
            dayOfMonth()
            chars(" ")
            hour()
            chars(":")
            minute()
            chars(":")
            second()
        }

        val currentTimeInstant = Clock.System.now()
        val timeFormat = currentTimeInstant.format(format)
        assertNotNull(timeFormat)
    }
}