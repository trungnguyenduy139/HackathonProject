package com.example.trungnguyen.hackathonproject.helper

import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class DownloadURL {

    @Throws(IOException::class)
    fun readUrl(myUrl: String): String {
        var data = ""
        var urlConnection: HttpURLConnection? = null

        val url = URL(myUrl)
        urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.connect()

        try {
            urlConnection.inputStream.use { inputStream ->
                val br = BufferedReader(InputStreamReader(inputStream))
                val sb = StringBuffer()
                while (br.readLine() != null) {
                    sb.append(br.readLine())
                }
                data = sb.toString()
                br.close()

            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection.disconnect()
        }
        Log.d("DownloadURL", "Returning data= " + data)
        return data
    }
}
