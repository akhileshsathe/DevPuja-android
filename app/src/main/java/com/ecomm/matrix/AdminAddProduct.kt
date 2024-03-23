package com.ecomm.matrix

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminAddProduct : AppCompatActivity() {

    private lateinit var btnIncrQnty:ImageButton
    private lateinit var btnDcrQnty:ImageButton
    private lateinit var btnAddImg:Button
    private lateinit var selectedImageUri: Uri
    private lateinit var productImageView:ImageView
    private lateinit var addProduct:Button

    private  lateinit var productNameEdt:EditText
    private lateinit var productCategoryEdt:EditText
    private lateinit var productDescriptionEdt:EditText
    private lateinit var productPriceEdt:EditText
    private lateinit var productCodeEdt:EditText
    private lateinit var productQuantityEdt:EditText
    private lateinit var productAvailableForSale:SwitchCompat




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_product)

        btnIncrQnty=findViewById(R.id.qntyIncrBtn)
        btnDcrQnty=findViewById(R.id.qntyDcrBtn)
        btnAddImg=findViewById(R.id.addImgBtn)
        productImageView=findViewById(R.id.product_imageView)

        productNameEdt=findViewById(R.id.edt_product_name)
        productCategoryEdt=findViewById(R.id.edt_product_category)
        productDescriptionEdt=findViewById(R.id.edt_product_description)
        productPriceEdt=findViewById(R.id.edt_product_price)
        productCodeEdt=findViewById(R.id.edt_product_code)
        productQuantityEdt=findViewById(R.id.edt_product_quantity)
        productAvailableForSale=findViewById(R.id.switch_available_for_sale)

        addProduct=findViewById(R.id.btnAddProduct)

        btnIncrQnty.setOnClickListener{

            productQuantityEdt.setText(productQuantityEdt.text.toString().toInt().inc().toString())
        }
        btnDcrQnty.setOnClickListener{
            if(productQuantityEdt.text.toString().toInt()>0 || productQuantityEdt.text.toString().isEmpty()){
                productQuantityEdt.setText(productQuantityEdt.text.toString().toInt().dec().toString())
            }


        }

        addProduct.setOnClickListener {



        }


        val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    selectedImageUri = it
//                    val f: File = File(it.path)
//                    val d = Drawable.createFromPath(f.absolutePath)
                    btnAddImg.visibility= View.GONE
                    productImageView.visibility=View.VISIBLE
                    productImageView.setImageURI(it)
                }
            }
        }

        // Handle image picker button click
        btnAddImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        }

        productImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        }




    }









}
