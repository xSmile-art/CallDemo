package com.smile.calldemo.common.utils

import android.util.Base64
import java.io.UnsupportedEncodingException


object Base64Utils {

    /**
     * 字符Base64加密
     * @param str
     * @return
     */
    fun encode(str: String): String {
        try {
            return Base64.encodeToString(str.toByteArray(charset("UTF-8")), Base64.DEFAULT)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 字符Base64解密
     * @param str
     * @return
     */
    fun decode(str: String): String {
        try {
            return String(Base64.decode(str.toByteArray(charset("UTF-8")), Base64.DEFAULT))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }
}