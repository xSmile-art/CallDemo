package com.smile.calldemo.bean

data class Online(
    val callbox: Callbox
)

data class Callbox(
    val keyNum: Int,
    val keys: List<Key>,
    val no: String
)

data class OnlineBean(
    val keyNum: Int,
    val keys: List<Key>,
    val no: String,
    val online: Boolean,
    val state: Int
)

data class Key(
    val id: Int,
    val state: Int? = 0,
    val taskId: Int? = 0,
    val time: Int?=0,
    val tsetId: Int? =0
)