package com.example.trungnguyen.hackathonproject.helper

import android.os.AsyncTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.io.IOException
import java.util.HashMap
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class GetNearbyTask : AsyncTask<Any, String, String>() {

    private var mGooglePlacesData: String? = null
    private var mMap: GoogleMap? = null

    override fun doInBackground(vararg objects: Any): String {
        mMap = objects[0] as GoogleMap
        val dataBuilder = StringBuilder()
        try {
            val url = URL(objects[1] as String)
            val reader = BufferedReader(InputStreamReader(url.openStream()))
            var line: String
            while (true) {
                line = reader.readLine() ?: break
                dataBuilder.append(line)
            }
            reader.close()
            mGooglePlacesData = dataBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            mGooglePlacesData = ConstHelper.PARSER_ERROR
        }
        return mGooglePlacesData!!
    }

    override fun onPostExecute(json: String) {
        try {
            val nearbyPlaceList: List<HashMap<String, String>> = DataParser.parse(json)
            if (nearbyPlaceList.isEmpty()) return
            showNearbyPlaces(nearbyPlaceList)
        } catch (ignored: Exception) {

        }
    }

    private fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>) {
        for (index in nearbyPlaceList.indices) {
            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlaceList[index]

            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val lat = java.lang.Double.parseDouble(googlePlace["lat"])
            val lng = java.lang.Double.parseDouble(googlePlace["lng"])

            val latLng = LatLng(lat, lng)
            markerOptions.position(latLng)
            markerOptions.title(placeName + " : " + vicinity)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

            mMap?.addMarker(markerOptions)
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap?.animateCamera(CameraUpdateFactory.zoomTo(10f))
        }
    }
}
