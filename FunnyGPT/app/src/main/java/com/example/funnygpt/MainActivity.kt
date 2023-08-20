package com.example.funnygpt

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

        // reference to textView (Question)
        val txtQuestion = findViewById<TextView>(R.id.textQuestion)

        // reference to textView (Answer)
        val txtAnswer = findViewById<TextView>(R.id.textAnswer)

        // Nhận nút bấm từ user thì làm gì
        btn_click_me.setOnClickListener {
            // Nhận input từ user sau khi user bấm nút gửi
            val et = findViewById<EditText>(R.id.inputPrompt)
            val prompt = et.text
            val prompt_str = prompt.toString()

            // Hiển thị input lên màn hình và xoá đoạn text được nhập vào ở EditText
            txtQuestion.setText("Question: " + prompt)
            et.setText("")

            getResponse(prompt_str) { response ->
                runOnUiThread {
                    txtAnswer.text = response
                }
            }
        }
    }

    fun getResponse(question: String, callback: (String) -> Unit) {
        val apiKey = "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" // provided key from KAIST
        val apiUrl = "https://api.openai.com/v1/completions"

        val requestBody = """
            {
            "model": "text-davinci-003",
            "prompt": "$question",
            "max_tokens": 4000,
            "temperature": 0
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

                var jsonObject = JSONObject(body)
                val jsonArray:JSONArray = jsonObject.getJSONArray("choices")
                val textAnswer = "Answer: " + jsonArray.getJSONObject(0).getString("text")
                callback(textAnswer)
            }
        })
    }
}