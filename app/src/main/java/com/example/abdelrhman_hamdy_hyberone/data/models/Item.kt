package com.example.abdelrhman_hamdy_hyberone.data.models

import com.example.abdelrhman_hamdy_hyberone.utils.DownloadStatus


data class Item(
    var id: Int,
    var type: String,
    var url: String,
    var name: String,
    var status: DownloadStatus? = null,
    var downloadPercentage: Int = 0
)