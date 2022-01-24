package com.example.abdelrhman_hamdy_hyberone.data.repository

import com.example.abdelrhman_hamdy_hyberone.data.api.ApiHelper
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getItems() = apiHelper.getItems()
}