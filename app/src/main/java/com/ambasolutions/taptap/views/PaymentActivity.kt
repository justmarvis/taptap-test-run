package com.ambasolutions.taptap.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.*
import com.stripe.android.model.Card
import com.stripe.android.model.PaymentMethod
import com.stripe.android.view.CardInputWidget
//import kotlinx.android.synthetic.main.payment_activity.*
import com.ambasolutions.taptap.R
import com.stripe.android.model.PaymentMethodCreateParams

class PaymentActivity : AppCompatActivity() {

    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

//        stripe = Stripe(this, "pk_test_51OCDJxK5xRd5h8fExDCwYQyp5q8SJuU1D6xyfngPp45CBGWkOPLAMKKycYvy06Q8YZtxfiAdjfapBpd7I40hC2XR00NEWJiaIv")
//
//        btnPay.setOnClickListener {
//            val card = cardInputWidget.cardParams
//
//            stripe.createPaymentMethod(
//                PaymentMethodCreateParams.create(card.toPaymentMethodParamsCard(), null),
//                object : ApiResultCallback<PaymentMethod> {
//                    override fun onSuccess(result: PaymentMethod) {
//                        // Handle successful PaymentMethod creation
//                        val paymentMethodId = result.id
//                        // Send paymentMethodId to your server for further processing
//                    }
//
//                    override fun onError(e: Exception) {
//                        // Handle error
//                    }
//                }.toString()
//            )
//        }
    }
}
