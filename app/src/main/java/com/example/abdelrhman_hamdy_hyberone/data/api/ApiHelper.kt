// ApiHelper will help ApiService to be accessed via repository maintaining encapsulation
package com.example.abdelrhman_hamdy_hyberone.data.api

import com.example.abdelrhman_hamdy_hyberone.data.models.Item
import retrofit2.Response

interface ApiHelper {
    suspend fun getItems(): Response<List<Item>>
}