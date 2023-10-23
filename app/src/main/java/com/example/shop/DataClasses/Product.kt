package com.example.shop.DataClasses

import java.io.Serializable

data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val id: Int,
    val price: Int,
    val rating: Double,
    val thumbnail: String,
    val images: List<String>,
    val title: String,
) : Serializable