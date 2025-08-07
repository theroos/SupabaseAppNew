package com.example.supabaseappnew

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.jan.supabase.createSupabaseClient
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

class StorageActivity : AppCompatActivity() {


    private lateinit var supabase1: SupabaseClient
    private lateinit var storageRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_storage)

        storageRecyclerView = findViewById(R.id.media_recyclerview)

        supabase1 = createSupabaseClient(
            supabaseUrl = "https://aaukmjyjnmgsbgrtwzho.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFhdWttanlqbm1nc2JncnR3emhvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzY2OTY4MTMsImV4cCI6MjA1MjI3MjgxM30.R6Y-P3L2LAwf9UtmolyRI2tUfwO0zpQvlT6HARO8r6Y"
        )
        {
            install(Storage)
            install(Postgrest)
        }

        fetchImages()

    }


    private fun fetchImages() {
        lifecycleScope.launch {
            try{
                val response = supabase1.storage.from("images").list("concept kits")

                /*if(response.error != null){
                    Toast.makeText(this@StorageActivity, "Error: ${response.error}", Toast.LENGTH_LONG).show()
                    Log.e("StorageActivity", "Error: ${response.error}")
                    return@launch
                }*/

                if (response.isNotEmpty()){
                    val urls = response.map { file -> supabase1.storage.from("images").publicUrl("concept kits/${file.name}")
                    }
                    storageRecyclerView.layoutManager = GridLayoutManager(this@StorageActivity, 2)
                    storageRecyclerView.adapter = ImageAdapter(urls, supportFragmentManager)

                    Toast.makeText(this@StorageActivity, "Fetched ${urls.size} images", Toast.LENGTH_LONG).show()

                }else{

                    Toast.makeText(this@StorageActivity, "No images found", Toast.LENGTH_LONG).show()

                }

                /*val imagelist = response.data ?: emptyList()

                Toast.makeText(this@StorageActivity, "Fetched ${imagelist.size} images", Toast.LENGTH_LONG).show()

                val urls = imagelist.map { file -> supabase1.storage.from("images").publicUrl("concept kits/${file.name}).toString() }")}



                Toast.makeText(this@StorageActivity, "Fetched ${urls.size} images", Toast.LENGTH_LONG).show()*/

            } catch(e: Exception){
                Toast.makeText(this@StorageActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("StorageActivity", "Error: ${e.message}", e)
            }
        }
    }
}

