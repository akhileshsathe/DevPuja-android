package com.matrix.ecommerce.models
import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
    // Add more fields as needed based on your API response
)