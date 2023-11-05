package com.smile.calldemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.smile.calldemo.MainActivity
import com.smile.calldemo.R
import com.smile.calldemo.api.ServiceApi
import com.smile.calldemo.common.mmkv.MMKVUtil
import com.smile.calldemo.common.utils.Base64Utils
import com.smile.calldemo.common.utils.RetrofitUtils

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "LoginActivity"

    private val netT = "network"
    private val userK = "user"
    private val pwdK = "pwd"
    private val urlK = "url"
    private val tokenK = "token"

    private val user: EditText by lazy { findViewById(R.id.user) }
    private val pwd: EditText by lazy { findViewById(R.id.pwd) }
    private val url: EditText by lazy { findViewById(R.id.url) }
    private val login: Button by lazy { findViewById(R.id.login) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        val userStr = MMKVUtil.getString(netT, userK, "") ?: ""
        val pwdStr = MMKVUtil.getString(netT, pwdK, "") ?: ""
        val urlStr = MMKVUtil.getString(netT, urlK, "") ?: ""

        if (!TextUtils.isEmpty(userStr) && !TextUtils.isEmpty(pwdStr) && !TextUtils.isEmpty(urlStr)) {
            url.setText(urlStr)
            user.setText(userStr)
            pwd.setText(pwdStr)
        }

        login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            login -> {
                val userTxt = user.text.trim().toString()
                val pwdTxt = pwd.text.trim().toString()
                val urlTxt = url.text.trim().toString()


                val token = Base64Utils.encode("$userTxt:$pwdTxt")

                RetrofitUtils.instance.initRetrofit(urlTxt, token)

                MMKVUtil.putString(netT, userK, userTxt)
                MMKVUtil.putString(netT, pwdK, pwdTxt)
                MMKVUtil.putString(netT, urlK, urlTxt)
                MMKVUtil.putString(netT, tokenK, token)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}