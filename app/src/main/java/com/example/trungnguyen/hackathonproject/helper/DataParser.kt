package com.example.trungnguyen.hackathonproject.helper

import java.util.HashMap

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
object DataParser {

    private fun getPlace(googlePlaceJson: JSONObject): HashMap<String, String> {
        val googlePlaceMap = HashMap<String, String>()
        var placeName = "--NA--"
        var vicinity = "--NA--"
        val latitude: String
        val longitude: String
        val reference: String
        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name")
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity")
            }

            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat")
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng")

            reference = googlePlaceJson.getString("reference")

            googlePlaceMap.put("place_name", placeName)
            googlePlaceMap.put("vicinity", vicinity)
            googlePlaceMap.put("lat", latitude)
            googlePlaceMap.put("lng", longitude)
            googlePlaceMap.put("reference", reference)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return googlePlaceMap

    }

    private fun getPlaces(jsonArray: JSONArray?): List<HashMap<String, String>> {
        val count = jsonArray!!.length()
        val placeList = ArrayList<HashMap<String, String>>()
        var placeMap: HashMap<String, String>?
        for (index in 0 until count) {
            try {
                placeMap = getPlace(jsonArray.get(index) as JSONObject)
                placeList.add(placeMap)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return placeList
    }

    fun parse(jsonData: String): List<HashMap<String, String>> {
        if (jsonData == ConstHelper.PARSER_ERROR) {
            UtilHelper.showToast("Lỗi kết nối")
            return ArrayList<HashMap<String, String>>()
        }
        var jsonArray: JSONArray? = null
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(jsonData)
            jsonArray = jsonObject.getJSONArray("results")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return getPlaces(jsonArray)
    }
}
