package com.example.trungnguyen.hackathonproject.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat

import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.helper.ConstHelper
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.trungnguyen.hackathonproject.helper.GetNearbyTask
import com.example.trungnguyen.hackathonproject.helper.UtilHelper

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class MapsActivity : FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private lateinit var mMap: GoogleMap
    private var mClient: GoogleApiClient? = null
    private var mRequest: LocationRequest? = null
    private var mLastLocation: Location? = null
    private var mCurrentMarker: Marker? = null
    private var mLatitude = 0.0
    private var mLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mLatitude = intent.getDoubleExtra(ConstHelper.LATITUDE, 0.0)
        mLongitude = intent.getDoubleExtra(ConstHelper.LONGITUDE, 0.0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkLocationPermission()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapFrag) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (mClient == null) {
                        buildGoogleApiClient()
                    }
                    mMap.isMyLocationEnabled = true
                }
            } else {
                UtilHelper.showToast("Permission denied")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient()
            mMap.isMyLocationEnabled = true
        }
    }

    @Synchronized private fun buildGoogleApiClient() {
        mClient = GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
        mClient!!.connect()
    }

    override fun onLocationChanged(location: Location) {

        if (mLongitude == 0.0 || mLatitude == 0.0) {
            mLatitude = location.latitude
            mLongitude = location.longitude
        }
        mLastLocation = location
        if (mCurrentMarker != null) {
            mCurrentMarker?.remove()

        }
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Location")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mCurrentMarker = mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10f))

        if (mClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this)
        }
        showMapResult()
    }

    private fun showMapResult() {
        if (mLatitude == 0.0 || mLongitude == 0.0) {
            UtilHelper.showToast("Tọa độ không hợp lệ")
            return
        }
        val dataTransfer = arrayOfNulls<Any>(2)
        val getNearbyPlacesData = GetNearbyTask()
        mMap.clear()
        val url = getApiUrl(mLatitude, mLongitude, ConstHelper.HOSPITAL)
        dataTransfer[0] = mMap
        dataTransfer[1] = url
        getNearbyPlacesData.execute(*dataTransfer)
        UtilHelper.showToast("Đang tìm những bệnh viện gần nhất")
    }

    private fun getApiUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
        val googlePlaceUrl = StringBuilder(ConstHelper.MAP_API_URL)
                .append("location=$latitude,$longitude")
                .append("&radius=" + ConstHelper.REQUEST_RADIUS)
                .append("&type=$nearbyPlace")
                .append("&sensor=true")
                .append("&key=${ConstHelper.API_KEY}")
        return googlePlaceUrl.toString()
    }

    override fun onConnected(bundle: Bundle?) {
        mRequest = LocationRequest()
        mRequest?.interval = 100
        mRequest?.fastestInterval = 1000
        mRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mRequest, this)
        }
    }

    private fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
            else ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
            false
        } else
            true
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    companion object {
        val REQUEST_LOCATION_CODE = 99
    }
}
