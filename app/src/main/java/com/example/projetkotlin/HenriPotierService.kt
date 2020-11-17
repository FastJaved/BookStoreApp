package com.example.projetkotlin

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HenriPotierService {
    @GET("books")
    fun listBooks(): Call<List<Book>>

    @GET("books/{id}/commercialOffers")
    fun getDiscount(@Path("id") id:String): Call<ResponseBody>
}