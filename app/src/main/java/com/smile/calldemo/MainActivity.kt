package com.smile.calldemo

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import com.smile.calldemo.api.api
import com.smile.calldemo.bean.BaseBean
import com.smile.calldemo.bean.Call
import com.smile.calldemo.bean.Callbox
import com.smile.calldemo.bean.Event
import com.smile.calldemo.bean.FindTask
import com.smile.calldemo.bean.Key
import com.smile.calldemo.bean.OfflineBean
import com.smile.calldemo.bean.Online
import com.smile.calldemo.bean.OrderTask
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    private val TAG = "MainActivity"

    private val calls = mutableListOf(
        "AA-BB-CC-DD-EE-A1",
        "AA-BB-CC-DD-EE-B1",
        "AA-BB-CC-DD-EE-A2",
        "AA-BB-CC-DD-EE-B2",
    )


    private val no: EditText by lazy { findViewById(R.id.no) }

    private val online: Button by lazy { findViewById(R.id.online_btn) }
    private val offline: Button by lazy { findViewById(R.id.offline_btn) }
    private val callHave: Button by lazy { findViewById(R.id.call_have_btn) }
    private val callNo: Button by lazy { findViewById(R.id.call_no_btn) }
    private val stop: Button by lazy { findViewById(R.id.end_btn) }

    private val selectedTv: TextView by lazy { findViewById(R.id.select_tv) }
    private val list: ListView by lazy { findViewById(R.id.list) }

    private var callSerial = ""
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initList()
        requestPermission()
    }

    private fun init() {

        online.setOnClickListener(this)
        offline.setOnClickListener(this)
        callHave.setOnClickListener(this)
        callNo.setOnClickListener(this)
        stop.setOnClickListener(this)

    }

    private fun initList() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, calls)
        list.adapter = adapter
        list.onItemClickListener = this
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        callSerial = calls[position]
        selectedTv.text = getString(R.string.selected_no, callSerial)
    }

    private fun requestPermission() {
        PermissionX.init(this@MainActivity)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { _, _, _ -> }
    }

    override fun onClick(v: View?) {
        when (v) {

            online -> {

                val no = callSerial()

                if (no.isEmpty()) {
                    toast(getString(R.string.selected_no_first))
                    return
                }

                request({
                    val callbox = Callbox(
                        4,
                        mutableListOf(Key(1), Key(2), Key(3), Key(4)),
                        no
                    )

                    api.online(Online(callbox))
                }, {

                    toast(getString(R.string.online_no, no))
                })
            }

            offline -> {

                val no = callSerial()

                if (no.isEmpty()) {
                    toast(getString(R.string.selected_no_first))
                    return
                }

                request({
                    val body = OfflineBean(no)

                    api.offline(body)
                }, {
                    toast(getString(R.string.offline_no, no))
                })
            }

            callNo -> {
                call(0)
            }

            callHave -> {
                call(1)
            }

            stop -> {
                request({
                    api.findTask(FindTask())
                }, {
                    it?.let {
                        it.tsets.forEach { item ->
                            val id = item.base.id

                            if (id > 0) {
                                request({
                                    api.orderTask(OrderTask(id.toString(), 3))
                                }, {
                                    toast(getString(R.string.cancel_task, id.toString()))
                                })
                            }
                        }
                    }
                })
            }
        }
    }

    private fun callSerial(): String {
        val noTxt = no.text.trim().toString()
        return noTxt.ifEmpty { callSerial }
    }

    private fun call(type: Int) {
        val no = callSerial()
        if (no.isEmpty()) {
            toast(getString(R.string.selected_no_first))
            return
        }
        request({
            val call = Call(Event(no, 1, type))
            api.call(call)
        }, {
            toast(getString(R.string.call_no, no))
        })
    }

    private fun <T> request(block: suspend () -> BaseBean<T>?, success: (T?) -> Unit): Job {

        return scope.launch {
            runCatching {
                block()
            }
                .onSuccess {
                    Log.e(TAG, it.toString())
                    if (it?.ok == true) {
                        success(it.body)
                    } else {
                        toast(getString(R.string.http_server_error))
                    }
                }
                .onFailure { e ->
                    toast(getString(R.string.http_server_error))
                    Log.e(TAG, e.toString())
                }
        }
    }


    private fun toast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }

}