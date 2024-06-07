package com.example.reviewerwriter.domain.entites

data class Status<T>(
    val statusCode: Int,
    val value: T?,
    val errors: Throwable?
)
