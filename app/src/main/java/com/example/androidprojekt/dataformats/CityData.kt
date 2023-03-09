package com.example.androidprojekt.dataformats

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CityData(
    val id:Float,
    val wikiDataId:String,
    val type: String,
    val city: String,
    val name: String,
    val country: String,
    val countryCode: String,
    val region: String,
    val regionCode: String,
    val latitude: Float,
    val longitude: Float,
    val population: Int
)