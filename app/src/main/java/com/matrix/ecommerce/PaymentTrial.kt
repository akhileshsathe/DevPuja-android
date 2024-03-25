package com.matrix.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import dev.shreyaspatil.easyupipayment.EasyUpiPayment

class PaymentTrial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_trial)

       val transactionID="TID" + System.currentTimeMillis()
        val easyUpiPayment = EasyUpiPayment(this) {
            this.payeeVpa = "8275644855@ybl"
            this.payeeName = "Matrix Infosystems"
            this.transactionId = transactionID
            this.payeeMerchantCode=""
            this.transactionRefId = transactionID
            this.description = "Description"
            this.amount = "1.00"


        }

//        easyUpiPayment.setPaymentStatusListener(this)
        val paymentButton =findViewById<Button>(R.id.MakePayment)

        paymentButton.setOnClickListener {


            easyUpiPayment.startPayment()
        }


    }
}