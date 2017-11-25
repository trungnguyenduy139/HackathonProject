package com.example.trungnguyen.hackathonproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.helper.ConstHelper

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class PatientDialog : Activity(), AdapterView.OnItemClickListener {

    private var mPatients: ArrayList<String>? = null
    private var mIsWarning: Boolean? = null

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        startDetailActivity(mPatients?.get(position)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_chooser)
        mPatients = intent.getStringArrayListExtra(ConstHelper.PATIENT_LIST)
        mIsWarning = intent.getBooleanExtra(ConstHelper.IS_WARNING, false)
        title = if (mIsWarning as Boolean) "Cảnh báo gần đây" else "Chọn bệnh nhân"
        if (mPatients?.size == 1) {
            startDetailActivity(mPatients?.get(0)!!)
            finish()
        }
        if (mPatients?.size == 0) mPatients?.add("Chưa có thông tin")
        val listView = findViewById<ListView>(R.id.lvPatient)
        val patientAdapter = ArrayAdapter<String>(this, R.layout.patient_item, mPatients)
        listView.adapter = patientAdapter
        listView.onItemClickListener = this
    }

    private fun startDetailActivity(patientId: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ConstHelper.PATIENT_ID, patientId)
        startActivity(intent)
    }
}