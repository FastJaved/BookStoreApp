package com.example.projetkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckOut : AppCompatActivity() {
    private lateinit var listView:ListView
    private lateinit var gridLayoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        setSupportActionBar(toolbar)
        gridLayoutManager = GridLayoutManager(this, 2)

        val basket = intent.getParcelableExtra<Basket>("basket")

        listView = findViewById<ListView>(R.id.checkout_list_view)

        val adapter = ListViewAdapter(this, basket!!.books)
        listView.adapter = adapter
        val fab: View = findViewById(R.id.floatingActionButton2)


       fab.setOnClickListener { view ->
           val url = "http://henri-potier.xebia.fr/"

           val retrofit = Retrofit.Builder()
               .baseUrl(url)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
           val service = retrofit.create(HenriPotierService::class.java)
           var listOfCode: ArrayList<String> =  arrayListOf<String>()
           for (item in basket.books) {
               listOfCode.add(item.isbn)
           }

           val discountCode:String = listOfCode.joinToString(separator = ",")
           Log.d("code", discountCode)
           val apiCall = service.getDiscount(discountCode)
           apiCall.enqueue(object: Callback<ResponseBody> {
               override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                   Log.d("url", apiCall.request().url.toString())
                   Log.d("rep",response.code().toString())
                   Log.d("discount", response.errorBody().toString())
               }

               override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                   TODO("Not yet implemented")
               }
           })
            }
        }

    }