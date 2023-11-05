package com.smile.calldemo.bean

data class Call(
    val event: Event
)

data class Event(
    val no: String,
    val key: Int,
    val type: Int
)