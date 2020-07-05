package com.mazur.stationsdistance.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Station (
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
): Serializable