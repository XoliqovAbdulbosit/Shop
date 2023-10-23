package com.example.shop.API

import com.example.shop.DataClasses.CartList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.POST
import com.example.shop.DataClasses.ProductData
import com.example.shop.DataClasses.Login
import com.example.shop.DataClasses.User
import retrofit2.http.Body

interface APIService {
    @GET("/products")
    fun getAll(): Call<ProductData>

    @GET("/products/categories")
    fun getCategories(): Call<List<String>>

    @GET("/products/category/{category}")
    fun getByCategory(@Path("category") category: String): Call<ProductData>

    @GET("/products/search")
    fun search(@Query("q") query: String): Call<ProductData>

    @POST("/auth/login")
    fun login(@Body login: Login): Call<User>

    @GET("/users/{id}/carts")
    fun getAllCarts(@Path("id") id: String): Call<CartList>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): Call<User>
}