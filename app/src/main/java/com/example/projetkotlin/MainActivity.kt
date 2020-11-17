package com.example.projetkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CellClickListener {
    private lateinit var adapter: RecyclerAdapter
    private var listOfBooks: ArrayList<Book> =  arrayListOf<Book>()
    private lateinit var gridLayoutManager: GridLayoutManager

    companion object{
        lateinit var basket: Basket
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        basket = Basket(arrayListOf<Book>())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        setSupportActionBar(toolbar)
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
                adapter = RecyclerAdapter(books=listOfBooks, cellClickListener=this@MainActivity)
                recyclerView.adapter = adapter
                Log.d("test",listOfBooks.toString())
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.e("Book", "Error : $t")
            }
        })
        }

    override fun onCellClickListener(data:Book) {
        val intent = Intent(this@MainActivity,SingleBookActivity::class.java)
        intent.putExtra("book",data)
        startActivity(intent)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.shop){
            if (basket.books.size > 0){
                val intent = Intent(this@MainActivity,CheckOut::class.java)
                intent.putExtra("basket", basket)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Basket is empty", Toast.LENGTH_LONG).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }



}




