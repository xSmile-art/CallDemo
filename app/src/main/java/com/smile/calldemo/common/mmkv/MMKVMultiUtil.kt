package com.smile.calldemo.common.mmkv


/**
 *@Description:
 *
 */

object MMKVMultiUtil {

    /**
     * 保存字符串
     *
     * @param id
     * @param key
     * @param value
     */
    fun putString(id: String, key: String, value: String): Boolean {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.putString(key, value).commit()
    }

    /**
     * 返回字符串
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getString(id: String, key: String, defValue: String): String? {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.getString(key, defValue)
    }

    /**
     * 保存布尔值
     *
     * @param id
     * @param key
     * @param value
     */
    fun putBoolean(id: String, key: String, value: Boolean): Boolean {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.putBoolean(key, value).commit()
    }

    /**
     * 返回布尔值
     *
     * @param id
     * @param key
     * @param defValue
     */
    fun getBoolean(id: String, key: String, defValue: Boolean): Boolean? {
        val sp = MMKVInstance.getPersistenceMulti(id)
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
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.putInt(key, value).commit()
    }

    /**
     * 返回int
     * @param id
     * @param key
     * @param defValue
     */
    fun getInt(id: String, key: String, defValue: Int): Int {
        val sp = MMKVInstance.getPersistenceMulti(id)
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
        val sp = MMKVInstance.getPersistenceMulti(id)
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
        val sp = MMKVInstance.getPersistenceMulti(id)
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
        val sp = MMKVInstance.getPersistenceMulti(id)
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
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.getLong(key, defValue)
    }


    fun getAll(id: String): Map<String, *> {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.all
    }

    /**
     * 删除
     *
     * @param id
     * @param key
     */
    fun remove(id: String, key: String): Boolean {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.remove(key).commit()
    }

    /**
     * 清除
     *
     * @param id
     */
    fun clear(id: String): Boolean {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.clear().commit()
    }

    /**
     * 异步提交
     *
     * @param id
     */
    fun apply(id: String) {
        val sp = MMKVInstance.getPersistenceMulti(id)
        sp.apply()
    }

    /**
     * 查询某个key是否已经存在
     */
    fun contains(id: String, key: String): Boolean {
        val sp = MMKVInstance.getPersistenceMulti(id)
        return sp.contains(key)
    }

    fun allKeys(id: String): Array<out String>? {
        val mmkv = MMKVManager.mmkvWithID_MULIT(id)
        return mmkv.allKeys()
    }
}