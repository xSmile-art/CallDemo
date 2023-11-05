package com.smile.calldemo.common.mmkv

import android.text.TextUtils

/**
 *@Description:
 *
 */
object MMKVUtil {

    /**
     * 保存String
     *
     * @param id
     * @param key
     * @param value
     */
    fun putString(id: String, key: String, value: String): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.putString(key, value).commit()
    }

    /**
     * 返回String
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getString(id: String, key: String, defValue: String): String? {
        val sp = MMKVInstance.getPersistence(id)
        return sp.getString(key, defValue)
    }

    /**
     * 保存boolean
     *
     * @param id
     * @param key
     * @param value
     */
    fun putBoolean(id: String, key: String, value: Boolean): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.putBoolean(key, value).commit()
    }

    /**
     * 返回boolean
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getBoolean(id: String, key: String, defValue: Boolean): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.getBoolean(key, defValue)
    }

    /**
     * 保存int
     *
     * @param id
     * @param key
     * @param value
     */
    fun putInt(id: String, key: String, value: Int): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.putInt(key, value).commit()
    }

    /**
     * 返回int
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getInt(id: String, key: String, defValue: Int): Int {
        val sp = MMKVInstance.getPersistence(id)
        return sp.getInt(key, defValue)
    }

    /**
     * 保存float
     *
     * @param id
     * @param key
     * @param value
     */
    fun putFloat(id: String, key: String, value: Float): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.putFloat(key, value).commit()
    }

    /**
     * 返回float
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getFloat(id: String, key: String, defValue: Float): Float {
        val sp = MMKVInstance.getPersistence(id)
        return sp.getFloat(key, defValue)
    }

    /**
     * 保存long
     *
     * @param id
     * @param key
     * @param value
     */
    fun putLong(id: String, key: String, value: Long): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.putLong(key, value).commit()
    }

    /**
     * 返回long
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getLong(id: String, key: String, defValue: Long): Long {
        val sp = MMKVInstance.getPersistence(id)
        return sp.getLong(key, defValue)
    }

    /**
     * 删除
     *
     * @param id
     * @param key
     */
    fun remove(id: String, key: String): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.remove(key).commit()
    }

    /**
     * 清除
     *
     * @param id
     */
    fun clear(id: String): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.clear().commit()
    }

    /**
     * 异步提交
     *
     * @param id
     */
    fun apply(id: String) {
        val sp = MMKVInstance.getPersistence(id)
        sp.apply()
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param id
     * @param key
     */
    fun contains(id: String, key: String): Boolean {
        val sp = MMKVInstance.getPersistence(id)
        return sp.contains(key)
    }

    fun allKeys(id: String): Array<out String>? {
        val mmkv = MMKVManager.mmkvWithId(id)
        return mmkv.allKeys()
    }

    fun getObjectValue(id: String, key: String): Any? {
        val mmkv = MMKVManager.mmkvWithId(id)
        // 因为其他基础类型value会读成空字符串，所以不是空字符串即为string or string-set 类型
        val value = mmkv.decodeString(key)
        if (!TextUtils.isEmpty(value)) {
            // 判断 string or string-set
            return if (value!![0] == 0x01.toChar()) {
                mmkv.decodeStringSet(key)
            } else {
                value
            }
        }

        // float double 类型可通过string-set配合判断
        // 通过数据分析可以看到类型为float或double时string类型为空字符串且string-set类型读出空数组
        // 最后判断float为0或NAN 的时候可以直接读成double类型，否则读float类型
        // 该判断方法对于非常小的double类型数据（0d < value <= 1.0569021313E-314）不生效
        val set = mmkv.decodeStringSet(key)
        if (set != null && set.size == 0) {
            val valueFloat = mmkv.decodeFloat(key)
            val valueDouble = mmkv.decodeDouble(key)
            return if (valueFloat.compareTo(0f) == 0 || valueFloat.compareTo(Float.NaN) == 0) {
                valueDouble
            } else {
                valueFloat
            }
        }

        // int long boolean 类型的处理放在一起，int 类型1和0等价于boolean类型的true和false
        // 判断long或int类型时，如果数据长度超出int的最大长度，则long 与int读出的数据不等，可确定为long类型
        val valueInt = mmkv.decodeInt(key)
        val valueLong = mmkv.decodeLong(key)
        return if (valueInt.toLong() != valueLong) {
            valueLong
        } else {
            valueInt
        }
    }

}