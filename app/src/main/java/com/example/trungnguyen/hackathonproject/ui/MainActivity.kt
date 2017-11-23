package com.example.trungnguyen.hackathonproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.TextView
import android.widget.Toast
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.bean.Patient
import com.example.trungnguyen.hackathonproject.helper.ApiHelper
import com.example.trungnguyen.hackathonproject.helper.NetworkUtil
import retrofit2.Call
import retrofit2.Response

/**
 * Author : Trung Nguyen
 * Date : 11/23/2017
 */

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, ApiHelper.ApiCallback {

    override fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
        val data = response?.body()!![0]
        txtTemperature.text = data.temperature
    }

    override fun onFailed() {
    }

    override fun onRefresh() {

    }

    private lateinit var txtTemperature: TextView
    private lateinit var txtSPO2: TextView
    private lateinit var txtBeat: TextView
    private lateinit var txtState: TextView
    private lateinit var txtPatient: TextView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val mApiHelper = ApiHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        if (NetworkUtil.isNetworkAvailable) mApiHelper.getDataProcess()
        else showToast("Kiểm tra kết nối mạng")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        txtTemperature = findViewById(R.id.txtTemperature)
        txtSPO2 = findViewById(R.id.txtSPO2)
        txtBeat = findViewById(R.id.txtBeat)
        txtState = findViewById(R.id.txtState)
        txtPatient = findViewById(R.id.txtPatient)
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }
}
