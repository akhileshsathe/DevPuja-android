package com.matrix.ecommerce.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.matrix.ecommerce.databinding.ActivityCartBinding



class Cart : AppCompatActivity() {
    private lateinit var binding:ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate((layoutInflater))
        setContentView(binding.root)




    }
}