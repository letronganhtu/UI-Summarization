package com.example.prompting_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fakegrab_login)

        val spinner = findViewById<Spinner>(R.id.Select_Country)
        val country_code = resources.getStringArray(R.array.CountryCode)

        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, country_code)
            spinner.adapter = adapter
        }
    }
}