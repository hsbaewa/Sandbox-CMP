package kr.co.hs.sandbox.presentation

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSUriLauncher : UriLauncher {
    override fun launchWebUrl(
        url: String
    ): Boolean = UIApplication.sharedApplication.let { app ->
        val webUrl = NSURL(string = url)
        if (app.canOpenURL(webUrl)) {
            UIApplication.sharedApplication.openURL(
                url = NSURL(string = url),
                options = mapOf<Any?, Any?>(),
                completionHandler = null
            )
            true
        } else {
            false
        }
    }
}