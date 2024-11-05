package org.example.notionegateway.exception

data class ApiException(
    val status: Int,
    val message: String?
)