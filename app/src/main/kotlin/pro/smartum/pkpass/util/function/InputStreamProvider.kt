package pro.smartum.pkpass.util.function

import android.content.Context
import android.net.Uri
import okhttp3.OkHttpClient
import okhttp3.Request
import pro.smartum.pkpass.model.InputStreamWithSource
import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL

val IPHONE_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53"

fun fromURI(context: Context, uri: Uri): InputStreamWithSource? {
    when (uri.scheme) {
        "content" ->
            return fromContent(context, uri)
        "http", "https" ->
            // TODO check if SPDY should be here
            return fromOKHttp(uri)
        "file" ->
            return getDefaultInputStreamForUri(uri)
        else ->
            return getDefaultInputStreamForUri(uri)
    }
}

private fun fromOKHttp(uri: Uri): InputStreamWithSource? {
    try {
        val client = OkHttpClient()
        val url = URL(uri.toString())
        val requestBuilder = Request.Builder().url(url)

        // fake to be an iPhone in some cases when the server decides to send no passbook
        // to android phones - but only do it then - we are proud to be Android ;-)
        val iPhoneFakeMap = mapOf(
                "air_canada" to "//m.aircanada.ca/ebp/",
                "air_canada2" to "//services.aircanada.com/ebp/",
                "icelandair" to "//checkin.si.amadeus.net",
                "mbk" to "//mbk.thy.com/",
                "heathrow" to "//passbook.heathrow.com/",
                "eventbrite" to "//www.eventbrite.com/passes/order"
        )

        for ((key, value) in iPhoneFakeMap)
            if (uri.toString().contains(value))
                requestBuilder.header("User-Agent", IPHONE_USER_AGENT)

        val request = requestBuilder.build()

        val response = client.newCall(request).execute()

        return InputStreamWithSource(uri.toString(), response.body().byteStream())
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

private fun fromContent(ctx: Context, uri: Uri): InputStreamWithSource? {
    return try {
        InputStreamWithSource(uri.toString(), ctx.contentResolver.openInputStream(uri)!!)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    }
}


private fun getDefaultInputStreamForUri(uri: Uri): InputStreamWithSource? {
    return try {
        InputStreamWithSource(uri.toString(), BufferedInputStream(URL(uri.toString()).openStream(), 4096))
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
