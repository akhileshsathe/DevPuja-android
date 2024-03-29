package com.matrix.ecommerce.models

data class Product(
    val avaialable_for_sale: Int,
    val available_quantity: Int,
    val category: String,
    val created_at: String,
    val description: String,
    val id: Int,
    val image_path: String,
    val name: String,
    val price: Double,
    val product_code: String,
    val updated_at: String,
    val net_quantity:String,
    val views:Int

)
