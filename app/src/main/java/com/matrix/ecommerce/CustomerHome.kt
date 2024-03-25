package com.matrix.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import com.matrix.ecommerce.databinding.ActivityCustomerHomeBinding

import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.create

class CustomerHome : AppCompatActivity() {
//    private lateinit var txt:TextView
    private lateinit var binding: ActivityCustomerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_customer_home)
        binding=ActivityCustomerHomeBinding.inflate((layoutInflater))
        setContentView(binding.root)
//        txt=findViewById(R.id.txt)
        getAPIProducts()

    }


    private fun getAPIProducts() {
       val retrofitBuilder=Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl(getString(R.string.LOCAL_API))
           .build()
           .create(ApiService::class.java)
        val retrofitData = retrofitBuilder.getProduct()
        retrofitData.enqueue(object : Callback<List<Product>?> {
            override fun onResponse(
                call: Call<List<Product>?>,
                response: Response<List<Product>?>
            ) {
               val responseBody=response.body()!!
                val stringBuilder =StringBuilder()
                for(product in responseBody){
                    stringBuilder.append(product.name)
                    stringBuilder.append("\n")


                }
                Log.d("Matrix - Retrofit", stringBuilder.toString())
                binding.txt.text=stringBuilder
            }

            override fun onFailure(call: Call<List<Product>?>, t: Throwable) {
                Log.d("Matrix - Retrofit", t.message.toString())
            }
        })

    }
}