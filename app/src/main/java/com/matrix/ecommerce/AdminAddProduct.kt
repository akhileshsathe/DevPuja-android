package com.matrix.ecommerce


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.matrix.ecommerce.databinding.ActivityAdminAddProductBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
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
import java.io.FilePermission

class AdminAddProduct : AppCompatActivity() {
    lateinit var imageUri: Uri
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

            binding.edtProductQuantity
                .setText(binding.edtProductQuantity.text.toString().toInt().inc().toString())
        }
        binding.qntyDcrBtn.setOnClickListener {
            if (binding.edtProductQuantity.text.toString()
                    .toInt() > 0 || binding.edtProductQuantity.text.toString().isEmpty()
            ) {
                binding.edtProductQuantity.setText(
                    binding.edtProductQuantity.text.toString().toInt().dec().toString()
                )
            }
        }

        binding.btnAddImageFromGallery.setOnClickListener {
            galleryContract.launch("image/*")
        }

        binding.btnAddImageFromCamera.setOnClickListener {
            cameraContract.launch(imageUri)
        }

        binding.btnAddProduct.setOnClickListener {
            addProduct()
        }


    }

    private fun addProduct() {
        val filesDir=applicationContext.filesDir
        val file=File(filesDir,"image.png")
        val inputStream = contentResolver.openInputStream(imageUri)
        val outputStream=FileOutputStream(file)
        inputStream!!.copyTo((outputStream))

        val requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image",file.name,requestBody)
        val availableSwitchValue =if (binding.switchAvailableForSale.isChecked) "1" else "0"
        val avaialable_for_sale = availableSwitchValue.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val available_quantity = binding.edtProductQuantity.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val category =binding.edtProductCategory.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val description =binding.edtProductDescription.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val name =binding.edtProductName.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val price= binding.edtProductPrice.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val product_code =binding.edtProductCode.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

//
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(HttpLoggingInterceptor())


        val retrofitBuilder=Retrofit.Builder()
            .baseUrl(getString(R.string.LOCAL_API))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)






        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            retrofitBuilder.createNewProduct(part, avaialable_for_sale, available_quantity, category, description, name, price, product_code)
                .enqueue(object : Callback<List<Product>> {
                    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                        if (response.isSuccessful) {
                            val products = response.body()
                            // Handle successful response here
                            if (products != null) {
                                for (product in products) {
                                    Log.d("Matrix.Ecommerce.Retrofit", "Product: $product")
                                }
                            } else {
                                Log.e("Matrix.Ecommerce.Retrofit", "Response body is null")
                            }
                        } else {
                            // Handle error response here
                            Log.e("Matrix.Ecommerce.Retrofit", "Error: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        // Handle failure here
                        t.printStackTrace()
                    }
                })
        }
    }
    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            applicationContext, "com.matrix.ecommerce.fileProvider", image
        )
    }


}










