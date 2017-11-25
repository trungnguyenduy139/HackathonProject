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

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        startDetailActivity(mPatients?.get(position)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_chooser)
        val listView = findViewById<ListView>(R.id.lvPatient)
        mPatients = intent.getStringArrayListExtra(ConstHelper.PATIENT_LIST)
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