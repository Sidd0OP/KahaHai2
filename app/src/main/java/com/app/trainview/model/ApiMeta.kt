package com.app.trainview.model

data class ApiMeta(
    val timestamp: String,
    val traceId: String,
    val source: String,
    val executionTime: Long,
)
