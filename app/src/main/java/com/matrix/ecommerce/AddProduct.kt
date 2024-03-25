package com.matrix.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.matrix.ecommerce.databinding.ActivityAddProductBinding


class AddProduct : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddProductBinding.inflate((layoutInflater))
        setContentView(binding.root)
    }
}