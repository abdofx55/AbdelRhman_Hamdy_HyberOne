// ApiHelperImpl implement ApiHelper to provide functionality to ApiHelper functions
// we have injected ApiService in the constructor itself so we can have only one instance of ApiService
// throughout the application lifecycle for any number of network calls.
package com.example.abdelrhman_hamdy_hyberone.data.api

import com.example.abdelrhman_hamdy_hyberone.data.models.Item
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getItems(): Response<List<Item>> = apiService.getItems()
}