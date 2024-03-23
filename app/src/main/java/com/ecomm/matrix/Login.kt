package com.ecomm.matrix

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



class Login : AppCompatActivity() {

    private lateinit var backBtn: ImageButton
    private lateinit var signUpTxt: TextView
    private lateinit var resetPasswordBtn: TextView
    private lateinit var loginBtn:Button
    private lateinit var emailEdt: EditText
    private lateinit var passwordEdt: EditText

    private lateinit var emailLayoutEdt: TextInputLayout
    private lateinit var passLayoutEdt: TextInputLayout
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        backBtn=findViewById(R.id.btnBack)

        backBtn.setOnClickListener {

                finish()

        }

        auth = Firebase.auth

        val sharedPrefs=getSharedPreferences("RentAToy",MODE_PRIVATE)
        val mEditor = sharedPrefs.edit()

        loginBtn=findViewById(R.id.btnLogin)
        signUpTxt=findViewById(R.id.txtSignup)
        resetPasswordBtn=findViewById(R.id.txtResetPassword)

        emailLayoutEdt = findViewById(R.id.edtEmailLayout)
        passLayoutEdt = findViewById(R.id.edtPassTxtInpLayout)

        emailEdt=findViewById(R.id.edtEmail)
        passwordEdt=findViewById(R.id.edtPass)
        val trialBtn=findViewById<Button>(R.id.btnTrial)
        val trialBtn2=findViewById<Button>(R.id.btnTrial2)
        val trialBtn3=findViewById<Button>(R.id.btnTrial3)
        val trialBtn4=findViewById<Button>(R.id.btnTrial4)



        resetPasswordBtn.setOnClickListener {
            val intent = Intent(this,ResetPassword::class.java)
            startActivity(intent)
        }
        trialBtn.setOnClickListener {
            val intent = Intent(this,AdminAddProduct::class.java)
            startActivity(intent)
        }
        trialBtn2.setOnClickListener {
            val intent = Intent(this,ProductDetailsView::class.java)
            startActivity(intent)
        }
        trialBtn3.setOnClickListener {
            val intent = Intent(this,Cart::class.java)
            startActivity(intent)
        }
        trialBtn4.setOnClickListener {
            val intent = Intent(this,CustomerHome::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener {

            val email=emailEdt.text.toString()
            val password=passwordEdt.text.toString()


            if(email.isEmpty()){
                emailLayoutEdt.error = "Required *"


            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailLayoutEdt.error = "Invalid Email Address"


            }
            else if(password.isEmpty()){
                emailLayoutEdt.error=null
                passLayoutEdt.error = "Required *"



            }
            else if(password.length<6){
                emailLayoutEdt.error=null
                passLayoutEdt.error = "Password is too short"

            }
            else{
                passLayoutEdt.error=null
                val tag= this.localClassName
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                           startActivity( Intent(this,CustomerHome::class.java).apply {
                               flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                           )



                            Log.d(tag, "signInWithEmail:success")
                            mEditor.apply()






//                        updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(tag, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
//                        updateUI(null)
                        }
                    }
            }
        }




        signUpTxt.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
    }
}