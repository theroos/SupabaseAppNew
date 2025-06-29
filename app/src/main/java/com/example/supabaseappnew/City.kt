package com.example.supabaseappnew
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    //val id: Int? = 0,
    //val city: String? = null,
    //val state: String? = null
    @SerialName("name") val city: String,
    @SerialName("states") val state: String,
    @SerialName("no") val number: Int
)
