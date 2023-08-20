package com.example.prompting

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // reference to button
        val btn_click_me = findViewById(R.id.send_button) as Button

        // reference to textView (Answer)
        val txtAnswer = findViewById<TextView>(R.id.textAnswer)

        // Nhận nút bấm từ user thì làm gì
        btn_click_me.setOnClickListener {
            // Nhận input từ user sau khi user bấm nút gửi
            val edit_text = findViewById<EditText>(R.id.inputPrompt)
            val prompt_str = edit_text.text.toString()

            // Xoá những gì user nhập vào sau khi bấm nút gửi
            edit_text.getText().clear()

            getResponse(prompt_str) { response ->
                runOnUiThread {
                    txtAnswer.text = response
                }
            }
        }
    }

    fun getResponse(prompt: String, callback: (String) -> Unit) {
        val apiKey = "" // provided key from KAIST
        val apiUrl = "https://api.openai.com/v1/chat/completions"

        val test_prompt = prompt.replace("\"", "").replace("\n", "")

        val prompt_1 = """<?xml version="1.0" encoding="utf-8"?>
                        <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:app="http://schemas.android.com/apk/res-auto"
                            xmlns:tools="http://schemas.android.com/tools"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            tools:context=".MainActivity">
                        
                            <TextView
                                android:id="@+id/Logo"
                                android:layout_width="109dp"
                                android:layout_height="60dp"
                                android:layout_marginStart="24dp"
                                android:layout_marginTop="24dp"
                                android:text="FakeGrab"
                                android:textSize="25sp"
                                android:textColor="@color/teal_700"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <TextView
                                android:id="@+id/Welcome_text"
                                android:layout_width="87dp"
                                android:layout_height="28dp"
                                android:layout_marginStart="24dp"
                                android:layout_marginTop="56dp"
                                android:text="Welcome!"
                                android:textSize="20sp"
                                android:textStyle="bold"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/Logo" />
                        
                            <TextView
                                android:id="@+id/Require_Text"
                                android:layout_width="362dp"
                                android:layout_height="23dp"
                                android:layout_marginStart="24dp"
                                android:layout_marginTop="8dp"
                                android:text="Enter your mobile number to continue"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/Welcome_text" />
                        
                            <Spinner
                                android:id="@+id/Select_Country"
                                android:layout_width="143dp"
                                android:layout_height="45dp"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="60dp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/Require_Text" />
                        
                            <EditText
                                android:id="@+id/inputPhone"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginTop="60dp"
                                android:layout_marginEnd="16dp"
                                android:ems="10"
                                android:inputType="phone"
                                app:layout_constraintEnd_toEndOf="parent"
                                app:layout_constraintStart_toEndOf="@+id/Select_Country"
                                app:layout_constraintTop_toBottomOf="@+id/Require_Text" />
                        
                            <TextView
                                android:id="@+id/AnotherLoginText"
                                android:layout_width="318dp"
                                android:layout_height="27dp"
                                android:layout_marginBottom="100dp"
                                android:text="Or continue with a social account"
                                android:gravity="center"
                                app:layout_constraintBottom_toBottomOf="parent"
                                app:layout_constraintEnd_toEndOf="parent"
                                app:layout_constraintStart_toStartOf="parent" />
                        
                            <Button
                                android:id="@+id/FB_button"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginStart="44dp"
                                android:layout_marginTop="16dp"
                                android:text="Facebook"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/AnotherLoginText" />
                        
                            <Button
                                android:id="@+id/GG_button"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginTop="16dp"
                                android:layout_marginEnd="44dp"
                                android:text="Google"
                                app:layout_constraintEnd_toEndOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/AnotherLoginText" />
                        
                            <Button
                                android:id="@+id/SubmitPhone"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginTop="324dp"
                                android:text="Enter"
                                app:layout_constraintEnd_toEndOf="parent"
                                app:layout_constraintHorizontal_bias="0.498"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                        </androidx.constraintlayout.widget.ConstraintLayout>""".replace("\"", "").replace("\n", "")

        val prompt_2 = """<?xml version="1.0" encoding="utf-8"?>
                        <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:app="http://schemas.android.com/apk/res-auto"
                            xmlns:tools="http://schemas.android.com/tools"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            tools:context=".MainActivity">
                        
                            <TextView
                                android:id="@+id/TotalBill_text"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="24dp"
                                android:text="Payment amount: ${'$'}500.00"
                                android:textSize="18sp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <TextView
                                android:id="@+id/textView4"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="48dp"
                                android:text="Contact Information"
                                android:textStyle="bold"
                                android:textSize="18sp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/TotalBill_text" />
                        
                            <TextView
                                android:id="@+id/Name"
                                android:layout_width="82dp"
                                android:layout_height="38dp"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="16dp"
                                android:text="Name:"
                                android:textSize="18sp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/textView4" />
                        
                            <EditText
                                android:id="@+id/inputName"
                                android:layout_width="238dp"
                                android:layout_height="47dp"
                                android:layout_marginStart="28dp"
                                android:layout_marginTop="124dp"
                                android:ems="10"
                                android:hint="Your full name"
                                android:inputType="textPersonName"
                                android:textSize="18sp"
                                app:layout_constraintStart_toEndOf="@+id/Name"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <TextView
                                android:id="@+id/Phone"
                                android:layout_width="82dp"
                                android:layout_height="38dp"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="16dp"
                                android:text="Phone:"
                                android:textSize="18sp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/Name" />
                        
                            <EditText
                                android:id="@+id/inputPhone"
                                android:layout_width="239dp"
                                android:layout_height="48dp"
                                android:layout_marginStart="28dp"
                                android:layout_marginTop="180dp"
                                android:ems="10"
                                android:inputType="phone"
                                android:hint="Your phone number"
                                android:textSize="18sp"
                                app:layout_constraintStart_toEndOf="@+id/Phone"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <TextView
                                android:id="@+id/Address"
                                android:layout_width="82dp"
                                android:layout_height="40dp"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="16dp"
                                android:text="Address:"
                                android:textSize="18sp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/Phone" />
                        
                            <EditText
                                android:id="@+id/inputAddress"
                                android:layout_width="237dp"
                                android:layout_height="44dp"
                                android:layout_marginStart="28dp"
                                android:layout_marginTop="240dp"
                                android:ems="10"
                                android:inputType="textPersonName"
                                android:hint="Your address"
                                android:textSize="18sp"
                                app:layout_constraintStart_toEndOf="@+id/Address"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <TextView
                                android:id="@+id/textView8"
                                android:layout_width="158dp"
                                android:layout_height="31dp"
                                android:layout_marginStart="16dp"
                                android:text="Promotion Code"
                                android:textStyle="bold"
                                android:textSize="18sp"
                                app:layout_constraintBottom_toBottomOf="parent"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/Address"
                                app:layout_constraintVertical_bias="0.067" />
                        
                            <EditText
                                android:id="@+id/pro_code"
                                android:layout_width="181dp"
                                android:layout_height="39dp"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="8dp"
                                android:ems="10"
                                android:hint="Enter a Valid Code: e.g. 123"
                                android:inputType="textPersonName"
                                android:textSize="14sp"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/textView8" />
                        
                            <Button
                                android:id="@+id/submit_pro_code"
                                android:layout_width="114dp"
                                android:layout_height="52dp"
                                android:layout_marginStart="44dp"
                                android:text="SUBMIT"
                                app:layout_constraintBottom_toBottomOf="parent"
                                app:layout_constraintStart_toEndOf="@+id/pro_code"
                                app:layout_constraintTop_toTopOf="parent"
                                app:layout_constraintVertical_bias="0.505" />
                        
                            <TextView
                                android:id="@+id/Ship"
                                android:layout_width="160dp"
                                android:layout_height="42dp"
                                android:layout_marginStart="16dp"
                                android:layout_marginTop="48dp"
                                android:text="Ship"
                                android:textSize="18sp"
                                android:textStyle="bold"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toBottomOf="@+id/pro_code" />
                        
                            <RadioGroup
                                android:id="@+id/shipping_addresses"
                                android:layout_width="match_parent"
                                android:layout_height="wrap_content"
                                android:layout_marginBottom="156dp"
                                android:orientation="vertical"
                                app:layout_constraintBottom_toBottomOf="parent"
                                app:layout_constraintEnd_toEndOf="parent"
                                app:layout_constraintHorizontal_bias="0.0"
                                app:layout_constraintStart_toStartOf="parent">
                        
                                <RadioButton
                                    android:id="@+id/fast_ship"
                                    android:layout_width="416dp"
                                    android:layout_height="37dp"
                                    android:layout_marginStart="16dp"
                                    android:layout_marginBottom="12dp"
                                    android:checked="true"
                                    app:layout_constraintBottom_toTopOf="@+id/normal_ship"
                                    app:layout_constraintEnd_toEndOf="parent"
                                    app:layout_constraintHorizontal_bias="0.0"
                                    app:layout_constraintStart_toStartOf="parent" />
                        
                                <RadioButton
                                    android:id="@+id/normal_ship"
                                    android:layout_width="416dp"
                                    android:layout_height="32dp"
                                    android:layout_marginStart="16dp"
                                    android:layout_marginBottom="12dp"
                                    android:checked="false"
                                    app:layout_constraintBottom_toBottomOf="parent"
                                    app:layout_constraintEnd_toEndOf="parent"
                                    app:layout_constraintHorizontal_bias="0.0"
                                    app:layout_constraintStart_toStartOf="parent" />
                            </RadioGroup>
                        
                            <TextView
                                android:id="@+id/textView10"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginStart="52dp"
                                android:layout_marginTop="480dp"
                                android:text="Fast ship (2 hours)"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <TextView
                                android:id="@+id/textView11"
                                android:layout_width="148dp"
                                android:layout_height="22dp"
                                android:layout_marginStart="52dp"
                                android:layout_marginTop="526dp"
                                android:text="Normal ship (3-5 days)"
                                app:layout_constraintStart_toStartOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                            <Button
                                android:id="@+id/Payment_button"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginEnd="36dp"
                                android:layout_marginBottom="100dp"
                                android:text="PAY"
                                app:layout_constraintBottom_toBottomOf="parent"
                                app:layout_constraintEnd_toEndOf="parent" />
                        
                        </androidx.constraintlayout.widget.ConstraintLayout>""".replace("\"", "").replace("\n", "")

        val requestBody = """
            {
            "model": "gpt-3.5-turbo",
            "messages": [{
                            "role": "system",
                            "content": "You are a helpful assistant who can generate a brief summary of a given UI screen."
                         },
                         {
                            "role": "user",
                            "content": "Summarize the UI screen which is in XML syntax, delimited by triple backticks, in at most 30 words. UI Screen (in XML syntax): ```$prompt_1```"
                         },
                         {
                            "role": "assistant",
                            "content": "This UI Screen provides sign up page of FakeGrab application via phone number or Facebook/Google account."
                         },
                         {
                            "role": "user",
                            "content": "Summarize the UI screen which is in XML syntax, delimited by triple backticks, in at most 30 words. UI Screen (in XML syntax): ```$prompt_2```"
                         },
                         {
                            "role": "assistant",
                            "content": "This UI screen displays a payment form with total bill, contact information (name, phone, address), a promotion code input, and shipping options (fast and normal)."
                         },
                         {
                           "role": "user",
                           "content": "Your task is to generate a short summary of a given UI screen. Summarize the UI screen which is in XML syntax, delimited by triple backticks, in at most 30 words. UI Screen (in XML syntax): ```$test_prompt```"
                         }]
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", "API failed")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.v("data", body)
                }
                else {
                    Log.v("data", "empty")
                }

                val jsonObject = JSONObject(body)
                val jsonArray:JSONArray = jsonObject.getJSONArray("choices")

                val textAnswer = "Summarization: " + jsonArray.getJSONObject(0).getJSONObject("message").getString("content")
                callback(textAnswer)
            }
        })
    }
}