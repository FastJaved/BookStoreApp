package com.example.projetkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "http://henri-potier.xebia.fr/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(HenriPotierService::class.java)
        val books = service.listBooks()

        books.enqueue(object: Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val listOfBooks = response.body()
                Log.d("test",listOfBooks.toString())
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.e("Book", "Error : $t")
            }
        })
        }
}