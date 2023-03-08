package com.example.pruebatecnica.domain.model

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName

data class LocationItem(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitud: Double,
    @SerializedName("date") val date: Timestamp
)
