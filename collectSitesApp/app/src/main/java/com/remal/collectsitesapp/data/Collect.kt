package com.remal.collectsitesapp.data

import java.util.*

data class Collect @JvmOverloads constructor(
    var title: String = "",
    var url: String = "",
    var description: String = "",
    var isCompleted: Boolean = false,
    var id: String = UUID.randomUUID().toString()
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()
}
