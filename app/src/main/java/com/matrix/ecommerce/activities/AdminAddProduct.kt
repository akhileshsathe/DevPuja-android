package com.matrix.ecommerce.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import com.matrix.ecommerce.interfaces.ApiService
import com.matrix.ecommerce.R
import com.matrix.ecommerce.databinding.ActivityAdminAddProductBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class AdminAddProduct : AppCompatActivity() {
    private lateinit var imageUri: Uri
    private lateinit var binding: ActivityAdminAddProductBinding


    private val cameraContract = registerForActivityResult(ActivityResultContracts.TakePicture()) {

        binding.imgProduct.setImageURI(imageUri)
    }
    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imgProduct.setImageURI(it)
        imageUri=it!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddProductBinding.inflate((layoutInflater))
        setContentView(binding.root)

        imageUri = createImageUri()!!

        binding.qntyIncrBtn.setOnClickListener {
            if(binding.edtProductQuantity.text.isNotEmpty())
            binding.edtProductQuantity
                .setText(binding.edtProductQuantity.text.toString().toInt().inc().toString())
            else
                binding.edtProductQuantity
                    .setText("0")
        }
        binding.qntyDcrBtn.setOnClickListener {
            if(binding.edtProductQuantity.text.isNotEmpty()){
                if (binding.edtProductQuantity.text.toString()
                        .toInt() > 0 || binding.edtProductQuantity.text.toString().isEmpty()
                ) {
                    binding.edtProductQuantity.setText(
                        binding.edtProductQuantity.text.toString().toInt().dec().toString()
                    )
                }
            }

            else
                binding.edtProductQuantity
                    .setText("0")
        }

        binding.btnAddImageFromGallery.setOnClickListener {
            galleryContract.launch("image/*")
        }

        binding.btnAddImageFromCamera.setOnClickListener {
            imageUri = createImageUri()!!
            cameraContract.launch(imageUri)
        }

        binding.btnAddProduct.setOnClickListener {
            addProduct()
        }



    }

    private fun addProduct() {
        binding.loadingProgressBar.visibility= View.VISIBLE
        val filesDir=applicationContext.filesDir
        val file= File(filesDir, "image.png")
        val inputStream = contentResolver.openInputStream(imageUri)
        val outputStream= FileOutputStream(file)
        inputStream!!.copyTo((outputStream))

        val requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", file.name, requestBody)
        val availableSwitchValue =if (binding.switchAvailableForSale.isChecked) "1" else "0"
        val available_for_sale = availableSwitchValue.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val available_quantity = binding.edtProductQuantity.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val category =binding.edtProductCategory.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val description =binding.edtProductDescription.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val name =binding.edtProductName.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val price= binding.edtProductPrice.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val net_quantity= binding.edtProductNetQuantity.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val unit_of_measure = binding.edtProductUnitMeasure.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val product_code =binding.edtProductCode.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .build()


        val retrofitBuilder= Retrofit.Builder()
            .baseUrl(getString(R.string.LOCAL_API))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)






        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

            retrofitBuilder.createNewProduct(part, available_for_sale, available_quantity, category, description, name, price, net_quantity,unit_of_measure, product_code)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful && response.body() != null) {
                            val rawResponse = response.body()?.string()
                            Log.d("Raw Response", rawResponse ?: "Empty Response Body")
//                            Toast.makeText(baseContext, "Product added successfully", Toast.LENGTH_SHORT).show()
                            Snackbar.make(
                                binding.root,
                                "Product added successfully",
                                Snackbar.LENGTH_SHORT
                            ).show()

                        } else {
                            // Handle error response
                            Log.e(
                                "Raw Response",
                                "Error: ${response.code()} - ${response.message()}"
                            )
                            Snackbar.make(
                                binding.root,
                                "Failed to add new Product",
                                Snackbar.LENGTH_SHORT
                            ).show()
//                            Toast.makeText(baseContext, "Failed to add new Product", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // Handle failure here
                        Log.e("Raw Response", "Failure: ${t.message}")
//                        Toast.makeText(baseContext, "Failed to add new Product ${t.message}", Toast.LENGTH_SHORT).show()
                        Snackbar.make(
                            binding.root,
                            "Failed to add new Product",
                            Snackbar.LENGTH_SHORT
                        ).show()

                    }
                })

        }
        inputStream.close()
        binding.loadingProgressBar.visibility= View.GONE
    }
    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            applicationContext, "com.matrix.ecommerce.fileProvider", image
        )
    }


}