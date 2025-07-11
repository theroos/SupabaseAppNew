package com.example.supabaseappnew

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StorageActivity : AppCompatActivity() {

    private lateinit var backtxt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_storage)

        backtxt = findViewById(R.id.back_txt)

        backtxt.setOnClickListener{
            finish()
        }
    }
}