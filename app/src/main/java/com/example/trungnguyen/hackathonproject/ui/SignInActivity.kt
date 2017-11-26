package com.example.trungnguyen.hackathonproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.helper.PreferenceUtil
import com.example.trungnguyen.hackathonproject.helper.UtilHelper

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private var etUserName: TextInputEditText? = null
    private var etPassword: TextInputEditText? = null
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                if (checkForLogIn()) {
                    val id = etUserName?.text
                    PreferenceUtil.saveUserId(this, id.toString())
                    startActivity(Intent(this, LaunchActivity::class.java))
                }
            }
        }
    }

    private fun checkForLogIn(): Boolean {
        val userName = etUserName?.text.toString()
        val password = etPassword?.text.toString()
        if (userName.isEmpty() || password.isEmpty()) {
            UtilHelper.showToast("Vui lòng nhập đầy đủ thông tin")
            return false
        }
        //todo: Check login from server
        return true
    }

    private var btnLogin: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        if (PreferenceUtil.getUserId(this).isNotEmpty()) {
            startActivity(Intent(this, LaunchActivity::class.java))
            finish()
        }
        initViews()
    }

    private fun initViews() {
        btnLogin = findViewById(R.id.btn_login)
        btnLogin?.setOnClickListener(this)
        etUserName = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etPassword)
    }
}
