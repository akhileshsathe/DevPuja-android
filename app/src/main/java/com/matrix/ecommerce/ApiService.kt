package com.matrix.ecommerce

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiService {
@GET("products")
 fun getProduct():Call<List<Product>>

@Multipart
@Headers("Accept: application/json")
@POST("products")
 fun createNewProduct(
    @Part image: MultipartBody.Part,
    @Part("available_for_sale") available_for_sale: RequestBody,
    @Part("available_quantity") available_quantity: RequestBody,
    @Part("category") category: RequestBody,
    @Part("description") description: RequestBody,
    @Part("name") name: RequestBody,
    @Part("price") price: RequestBody,
    @Part("product_code") product_code: RequestBody

):Call<List<Product>>
}