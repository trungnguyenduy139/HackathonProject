package com.example.trungnguyen.hackathonproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.trungnguyen.hackathonproject.R
import android.content.Context
import android.view.View
import com.example.trungnguyen.hackathonproject.service.NotifyService
import android.text.TextUtils
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.example.trungnguyen.hackathonproject.helper.ConstHelper
import com.example.trungnguyen.hackathonproject.helper.PreferenceUtil
import com.example.trungnguyen.hackathonproject.helper.UtilHelper


/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class LaunchActivity : AppCompatActivity(), View.OnClickListener {

    private val mPatientList = ArrayList<String>()

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.txt_nearby_hospital -> checkForNearestHospital()
            R.id.txt_follow -> buildAlertFollowMsg()
            R.id.txt_current -> {
                if (mPatientList.size == 0) {
                    UtilHelper.showToast("Chưa có người bệnh cần theo dỗi")
                    buildAddPatientDialog()
                    return
                }
                startActivity(Intent(this, PatientDialog::class.java)
                        .putStringArrayListExtra(ConstHelper.PATIENT_LIST, mPatientList))
            }
        }
    }

    private fun buildAddPatientDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Thêm ID bệnh nhân")
        alert.setMessage("Vui lòng nhập ID bệnh nhân đã đc cung cấp")

        val input = EditText(this)
        alert.setView(input)

        alert.setPositiveButton("OK", { _, _ ->
            val patientId = input.text.toString()
            if (!patientId.isEmpty()) {
                mPatientList.add(patientId)
                UtilHelper.showToast("Nhập thành công")
                PreferenceUtil.saveListPatient(this, mPatientList)
            } else UtilHelper.showToast("Nhập ID")
        })

        alert.setNegativeButton("CANCEL", { dialog, _ ->
            dialog.dismiss()
        })

        alert.show()
    }

    private fun buildAlertFollowMsg() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông báo")
                .setMessage("Thiết bị của bạn sẻ đc thông báo khi tình trạng sức khỏe " +
                        "của ng bệnh ở mức nhất định, cần có internet để thông tin luôn cập nhật")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", { _, _ ->
                    startService(Intent(this, NotifyService::class.java))
                })
                .setNegativeButton("Hủy", { dialog, _ -> dialog.dismiss() })
        val alert = builder.create()
        alert.show()
    }

    private fun checkForNearestHospital() {
        if (isLocationEnabled(this)) {
            startActivity(Intent(this, MapsActivity::class.java)
                    .putExtra(ConstHelper.LONGITUDE, 0.0)
                    .putExtra(ConstHelper.LATITUDE, 0.0))
        } else buildAlertMessageNoGps()
    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("GPS của thiết bị đã bị tắt, vui lòng mở lại!")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", { _, _ ->
                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                })
                .setNegativeButton("Hủy", { dialog, _ -> dialog.dismiss() })
        val alert = builder.create()
        alert.show()
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationMode: Int
        val locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (ignored: Exception) {
                return false
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF

        } else {
            locationProviders = Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            return !TextUtils.isEmpty(locationProviders)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        initViews()
        val tempSet = PreferenceUtil.getListPatient(this)
        if (tempSet.isNotEmpty())
            PreferenceUtil.getListPatient(this).forEach { mPatientList.add(it) }
    }

    private fun initViews() {
        setClickListener(R.id.txt_nearby_hospital)
        setClickListener(R.id.txt_current)
        setClickListener(R.id.txt_follow)
    }

    private fun setClickListener(txtId: Int) {
        findViewById<TextView>(txtId).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.launch_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_patient -> buildAddPatientDialog()
        }
        return false
    }
}
