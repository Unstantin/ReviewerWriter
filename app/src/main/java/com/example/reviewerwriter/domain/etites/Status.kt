package com.example.reviewerwriter.domain.etites

data class Status<T>(
    val statusCode: Int,
    val value: T?,
    val errors: Throwable?
)
