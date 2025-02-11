package kr.co.hs.sandbox.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalyticsEvents
import dev.gitlive.firebase.analytics.FirebaseAnalyticsParam
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.analytics.logEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kr.co.hs.sandbox.presentation.DefaultPresenter
import kr.co.hs.sandbox.presentation.Presentation
import kr.co.hs.sandbox.presentation.Presenter

@Composable
fun DateTimePresentation(
    modifier: Modifier = Modifier,
    dateTimeSample: Presentation.DateTimeSample,
    presenter: Presenter = DefaultPresenter(rememberNavController())
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "text : ${dateTimeSample::class.simpleName}"
        )

        val timeInstant = Clock.System.now()
        Text(text = "timeInstant = $timeInstant")

        val timeZone = TimeZone.currentSystemDefault()
        val localDateTime = timeInstant.toLocalDateTime(timeZone)
        Text("year = ${localDateTime.year}")
        Text("month = ${localDateTime.monthNumber}")

        Text("millis = ${timeInstant.toEpochMilliseconds()}")

        Text("toInstant = ${localDateTime.toInstant(timeZone)}")

        val dateTime =
            Instant.fromEpochMilliseconds(dateTimeSample.millis).toLocalDateTime(timeZone)
        val format = LocalDateTime.Format {
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
        Text("dateFormat = ${dateTime.format(format)}")

        Button(
            onClick = {
                presenter.popToHome()
            },
            content = {
                Text(text = "home")
            }
        )
    }

    LaunchedEffect(Unit) {
        flow { emit(Firebase.analytics) }
            .onEach {
                it.logEvent(FirebaseAnalyticsEvents.VIEW_ITEM) {
                    param(FirebaseAnalyticsParam.ITEM_NAME, "DateTimePresentation")
                }
            }
            .catch {}
            .launchIn(this)
    }
}