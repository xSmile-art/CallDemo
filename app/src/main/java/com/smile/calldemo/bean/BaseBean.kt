package com.smile.calldemo.bean

data class BaseBean<T>(val msg: String, val ok: Boolean, val type: String?, val body: T?) {

    fun isSuccess() = ok
}
