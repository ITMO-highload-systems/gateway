package org.example.notionegateway

data class ApiException(
    val status: Int,
    val message: String?
)