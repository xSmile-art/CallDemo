package com.smile.calldemo.bean

data class FindTask(
    val id: Int = 0,
    val page: Int = 0,
    val robot: Int = 0,
    val route: String = "",
    val sort: Int = 0,
    val state: State = State(),
    val time: Time = Time()
)

data class OrderTask(
    val id: String,
    val order: Int
)

data class OrderTaskInfo(
    val id: Int,
    val order: Int,
    val robot: Int,
    val taskId: Int
)

data class State(
    val gte: Int = 1,
    val lte: Int = 2
)

data class Time(
    val gte: Int = 0,
    val lte: Int = 0
)

data class TaskInfo(
    val qty: String,
    val tsets: List<Tset>
)

data class Tset(
    val base: Base,
    val exec: Exec,
    val tasks: List<Task>,
    val time: TimeX
)

data class Base(
    val actuator: String,
    val adapter: String,
    val id: Int,
    val name: String,
    val no: String,
    val priority: Int,
    val robots: List<Int>,
    val route: String,
    val rtype: Int,
    val type: String
)

data class Exec(
    val curTaskId: Int,
    val preTaskId: Int,
    val robot: Int,
    val state: Int
)

data class Task(
    val base: BaseX,
    val exec: ExecX
)

data class TimeX(
    val end: Int,
    val generate: Int,
    val start: Int
)

data class BaseX(
    val cannotCancel: Boolean,
    val custom: String,
    val direction: Int,
    val id: Int,
    val markNo: String,
    val name: String,
    val type: Int,
    val typeNo: Int,
    val waitTime: Int
)

data class ExecX(
    val actionWaitId: Int,
    val state: Int
)