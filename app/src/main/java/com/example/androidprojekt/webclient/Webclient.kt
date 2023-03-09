package com.example.androidprojekt.webclient

import android.content.Context
import com.example.androidprojekt.R
import com.example.androidprojekt.dataformats.CityData
import com.example.androidprojekt.dataformats.RootData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Webclient {

    suspend fun requestCity(cityName: String, context: Context): CityData? {

        println("Requesting location of $cityName")

        val client = HttpClient(CIO)

        val url =  "https://wft-geo-db.p.rapidapi.com/v1/geo/cities"

        val apiKey: String = context.getString(R.string.apiKey)
        val apiHost: String = context.getString(R.string.apiHost)

        try {
            val response: HttpResponse = client.get(url) {
                url {
                    parameters.append("countryIds", "HU")
                    parameters.append("namePrefix", cityName)
                    parameters.append("types", "CITY")
                }
                headers {
                    headers.append("X-RapidAPI-Key", apiKey)
                    headers.append("X-RapidAPI-Host", apiHost)
                }
                method = HttpMethod.Get
            }
            val body: String = response.body()

            val json = Json { ignoreUnknownKeys = true }

            return json.decodeFromString<RootData>(body).data[0]

        } catch (e: java.lang.Exception) {
            println(e.toString())
        }
        client.close()

        return null

    }

}