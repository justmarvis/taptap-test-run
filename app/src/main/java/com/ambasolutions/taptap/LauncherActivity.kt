package com.ambasolutions.taptap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.widget.ImageView
import com.stripe.android.PaymentConfiguration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

val BackendUrl = "http://10.0.2.2:4242"

class LauncherActivity : AppCompatActivity() {
    private val httpClient = OkHttpClient()
    private lateinit var publishableKey: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchPublishableKey()

        setContentView(R.layout.activity_launcher)

    }

    private fun displayAlert(
        title: String,
        message: String
    ) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)

            builder.setPositiveButton("Ok", null)
            builder
                .create()
                .show()
        }
    }
    // Fetch publishable key from server and initialise the Stripe SDK
    private fun fetchPublishableKey() {
        val request = Request.Builder().url(BackendUrl + "config").build()

        httpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                displayAlert("Request failed", "Error: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    displayAlert("Request failed", "Error: $response")
                } else {
                    val responseData = response.body?.string()
                    val responseJson = responseData?.let {JSONObject(it)} ?: JSONObject()
                    publishableKey = responseJson.getString("publishableKey")

                    PaymentConfiguration.init(applicationContext, publishableKey)
                }
            }
        })
    }

}
