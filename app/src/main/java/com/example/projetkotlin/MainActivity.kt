package com.example.projetkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RecyclerAdapter
    private var listOfBooks: ArrayList<Book> =  arrayListOf<Book>()
    private lateinit var gridLayoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager


        val url = "http://henri-potier.xebia.fr/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(HenriPotierService::class.java)
        val books = service.listBooks()

        books.enqueue(object: Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                listOfBooks.addAll(response.body()!!)
                adapter = RecyclerAdapter(listOfBooks)
                recyclerView.adapter = adapter
                Log.d("test",listOfBooks.toString())
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.e("Book", "Error : $t")
            }
        })


        }

    override fun onStart() {
        super.onStart()
    }




}