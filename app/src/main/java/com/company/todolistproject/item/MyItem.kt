package com.company.todolistproject.item

data class MyItem (
    val id: Long = 0,
    var data: String = "",
    var text: String = "",
    var isDeleted: Boolean = false,
)