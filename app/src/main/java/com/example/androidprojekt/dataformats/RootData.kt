package com.example.androidprojekt.dataformats

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RootData(val data: Array<CityData>)