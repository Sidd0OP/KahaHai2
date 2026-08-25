package com.app.trainview.model

data class ApiResponse<T>(
    val success: Boolean = false,
    val data: T? = null,
    val meta: ApiMeta? = null
)