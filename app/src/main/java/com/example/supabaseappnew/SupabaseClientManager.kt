package com.example.supabaseappnew

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

import io.github.jan.supabase.storage.Storage


object SupabaseClientManager {

        private const val SUPABASE_URL = "https://aaukmjyjnmgsbgrtwzho.supabase.co" // Replace with your actual URL
        private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFhdWttanlqbm1nc2JncnR3emhvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzY2OTY4MTMsImV4cCI6MjA1MjI3MjgxM30.R6Y-P3L2LAwf9UtmolyRI2tUfwO0zpQvlT6HARO8r6Y" // Replace with your actual key

        val supabase: SupabaseClient = createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {

            install(Postgrest)
            install(Storage)
            install(Auth)
            // Add other plugins as needed
        }
    }
