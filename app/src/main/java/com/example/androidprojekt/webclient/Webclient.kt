package com.example.androidprojekt.webclient

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

    suspend fun requestCity(cityName: String): CityData? {

        println("Starting request")

        val client = HttpClient(CIO)

        val url =  "https://wft-geo-db.p.rapidapi.com/v1/geo/cities"

        val apiKey = "9f345495d4msh03e6585cdc223c1p16c1a4jsne51d83ef4eda"
        val apiHost = "wft-geo-db.p.rapidapi.com"

        try {
            val response: HttpResponse = client.get(url){
                url{
                    parameters.append("countryIds","HU")
                    parameters.append("namePrefix",cityName)
                    parameters.append("types","CITY")
                }
                headers{
                    headers.append("X-RapidAPI-Key",apiKey)
                    headers.append("X-RapidAPI-Host",apiHost)
                }
                method = HttpMethod.Get
            }
            val body: String = response.body()

            val json = Json { ignoreUnknownKeys = true }

            val cityData = json.decodeFromString<RootData>(body).data[0]

            return cityData

        }
        catch(e: java.lang.Exception){
            println(e.toString())
        }
        client.close()

        return null

    }

}