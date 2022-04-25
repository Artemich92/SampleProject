package com.sampleproject.data.remote.requests.auth

import com.google.gson.annotations.SerializedName

data class TestLogInRequest(
    @SerializedName("phone_number") val phoneNumber: Long,
    @SerializedName("password") val password: String
)
