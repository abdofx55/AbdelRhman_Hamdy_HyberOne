// ApiService will service our network call
package com.example.abdelrhman_hamdy_hyberone.data.api

import com.example.abdelrhman_hamdy_hyberone.data.models.Item
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("HyperoneWebservice/getListOfFilesResponse.json")
    suspend fun getItems(): Response<List<Item>>
}