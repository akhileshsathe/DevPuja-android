package com.matrix.ecommerce.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.matrix.ecommerce.R

class ResetPassword : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        backBtn = findViewById(R.id.btnBack)

        backBtn.setOnClickListener {
            finish()
        }
    }
}