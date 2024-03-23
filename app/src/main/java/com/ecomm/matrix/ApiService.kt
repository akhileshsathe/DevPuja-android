package com.ecomm.matrix

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
@GET("products")
fun getProduct():Call<List<Product>>
}