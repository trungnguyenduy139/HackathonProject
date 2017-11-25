package com.example.trungnguyen.hackathonproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.bean.Patient
import com.example.trungnguyen.hackathonproject.helper.ApiHelper
import com.example.trungnguyen.hackathonproject.helper.ConstHelper
import com.example.trungnguyen.hackathonproject.helper.NetworkUtil
import com.example.trungnguyen.hackathonproject.helper.UtilHelper
import retrofit2.Call
import retrofit2.Response

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, ApiHelper.ApiCallback, View.OnClickListener {

    private var mPatient: Patient? = null

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.txt_refresh -> onRefresh()
        }
    }

    override fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
        mSwipeRefreshLayout.isRefreshing = false
        val data = response?.body()!![0]
        mPatient = data
        txtPatient.text = data.ID
        txtTemperature.text = data.temperature
        txtSPO2.text = data.SPO2
        txtBeat.text = data.BEAT
        txtState.text = data.state
        txtHeartState.text = data.HEART_STATE
        txtLungState.text = data.LUNG_STATE
    }

    override fun onFailed() {
        mSwipeRefreshLayout.isRefreshing = false
        UtilHelper.showToast("Xẩy ra lỗi truy cập dữ liệu")
    }

    override fun onRefresh() {
        mSwipeRefreshLayout.isRefreshing = true
        makeRequest()
    }

    private lateinit var txtTemperature: TextView
    private lateinit var txtSPO2: TextView
    private lateinit var txtBeat: TextView
    private lateinit var txtState: TextView
    private lateinit var txtPatient: TextView
    private lateinit var txtHeartState: TextView
    private lateinit var txtLungState: TextView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val mApiHelper = ApiHelper(this)
    private lateinit var mId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        mId = intent.getStringExtra(ConstHelper.PATIENT_ID)
    }

    override fun onStart() {
        super.onStart()
        makeRequest()
    }

    private fun makeRequest() {
        if (NetworkUtil.isNetworkAvailable) mApiHelper.getDataProcess(mId)
        else UtilHelper.showToast("Kiểm tra kết nối mạng")
    }

    private fun initViews() {
        txtTemperature = findViewById(R.id.txtTemperature)
        txtSPO2 = findViewById(R.id.txtSPO2)
        txtBeat = findViewById(R.id.txtBeat)
        txtState = findViewById(R.id.txtState)
        txtPatient = findViewById(R.id.txtPatient)
        txtHeartState = findViewById(R.id.txtHeartState)
        txtLungState = findViewById(R.id.txtLungState)
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        findViewById<TextView>(R.id.txt_refresh).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detail_patient -> if (mPatient != null) {
                if (UtilHelper.isLocationEnabled(this)) {
                    startActivity(Intent(this, MapsActivity::class.java)
                            .putExtra(ConstHelper.LATITUDE, mPatient?.LAT?.toDouble())
                            .putExtra(ConstHelper.LONGITUDE, mPatient?.LONG?.toDouble()))
                } else UtilHelper.buildAlertMessageNoGps(this)
            } else UtilHelper.showToast("Chưa có dữ liệu bệnh nhân")
        }
        return false
    }
}
