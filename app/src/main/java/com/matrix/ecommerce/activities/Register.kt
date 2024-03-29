package com.matrix.ecommerce.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.matrix.ecommerce.R

class Register : AppCompatActivity() {
    private lateinit var backBtn: ImageButton
    private lateinit var loginButton: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confPassword: EditText
    private lateinit var fullNameEdt: EditText
    private lateinit var phoneEdt: EditText

    private lateinit var signupButton: Button



    private lateinit var auth: FirebaseAuth



    private lateinit var userTypeInputLayout: TextInputLayout
    private lateinit var fullNameLayoutEdt: TextInputLayout
    private lateinit var emailLayoutEdt: TextInputLayout
    private lateinit var phoneLayoutEdt: TextInputLayout
    private lateinit var passLayoutEdt: TextInputLayout

    private lateinit var confPassLayoutEdt: TextInputLayout





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        backBtn=findViewById(R.id.btnBack)

        backBtn.setOnClickListener {
            finish() }

//        val sharedPrefs=getSharedPreferences("Matrix",MODE_PRIVATE)
//        val mEditor = sharedPrefs.edit()
        //Firebase
        auth = Firebase.auth

        //EditText
        email = findViewById(R.id.edtEmail)
        password = findViewById(R.id.edtPass)
        confPassword = findViewById(R.id.edtConfPass)
        fullNameEdt = findViewById(R.id.edtFullName)
        phoneEdt = findViewById(R.id.edtPhone)

        fullNameEdt = findViewById(R.id.edtFullName)

        //buttons
        loginButton = findViewById(R.id.txtLoginBtn)
        signupButton = findViewById(R.id.btnSignUp)

        val userType = "C"

        fullNameLayoutEdt = findViewById(R.id.edtFullNameLayout)

        passLayoutEdt = findViewById(R.id.edtPassTxtInpLayout)
        confPassLayoutEdt = findViewById(R.id.edtConfPassTxtInpLayout)

        emailLayoutEdt = findViewById(R.id.edtEmailLayout)
        phoneLayoutEdt = findViewById(R.id.edtPhoneLayout)



        //Spinner







        loginButton.setOnClickListener {

            val intent = Intent(this, Login::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            startActivity(intent)
        }


        signupButton.setOnClickListener {
//            Toast.makeText(baseContext, "Signup button pressed", Toast.LENGTH_SHORT).show()
            val emailTxt = email.text.toString()
            val passTxt = password.text.toString()
            val confPassTxt = confPassword.text.toString()
            val fullNameTxt = fullNameEdt.text.toString()
            val phoneTxt = phoneEdt.text.toString()




            if (fullNameTxt.isEmpty()) {
                userTypeInputLayout.error = null
                fullNameLayoutEdt.error = "Required *"
            }
            else if (emailTxt.isEmpty()) {
                fullNameLayoutEdt.error = null
                emailLayoutEdt.error = "Required *"

            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                fullNameLayoutEdt.error = null
                emailLayoutEdt.error = "Enter a valid email"
            }
            else if (phoneTxt.isEmpty()) {
                emailLayoutEdt.error = null
                phoneLayoutEdt.error = "Required *"

            }
            else if (phoneTxt.length < 10 || phoneTxt.length > 10) {
                emailLayoutEdt.error = null
                phoneLayoutEdt.error = "Invalid phone"
            }

            else if (passTxt.isEmpty()) {

                phoneLayoutEdt.error = null

                passLayoutEdt.error = "Required *"


            } else if (passTxt.length < 6) {
                phoneLayoutEdt.error = null
                passLayoutEdt.error = "Password is too short"
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
            } else if (confPassTxt.isEmpty()) {
                passLayoutEdt.error = null
                confPassLayoutEdt.error = "Required *"


            } else if (passTxt != confPassTxt) {
                passLayoutEdt.error = null
                confPassLayoutEdt.error = "Passwords do not match."
            } else {

                confPassLayoutEdt.error = null
                val tag = this.localClassName
                auth.createUserWithEmailAndPassword(emailTxt, passTxt)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(tag, "createUserWithEmail:success")


                            val user = auth.currentUser

                            val profileUpdates = userProfileChangeRequest {
                                displayName = fullNameTxt
//                                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                            }

                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d("Register", "User profile updated.")
                                    }
                                }

//


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(tag, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }

        }
    }


}