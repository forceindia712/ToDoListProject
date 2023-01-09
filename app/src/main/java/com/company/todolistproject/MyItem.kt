package com.company.todolistproject

data class MyItem (
    val id: Long = 0,
    var data: String = "",
    var text: String = "",
    var isDeleted: Boolean = true,
)