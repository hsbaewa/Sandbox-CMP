package kr.co.hs.sandbox.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AndroidUriLauncher : UriLauncher, KoinComponent {

    private val context: Context = get()

    override fun launchWebUrl(
        url: String
    ): Boolean = runCatching {
        val webLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        webLinkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(webLinkIntent)
        true
    }.getOrDefault(false)
}