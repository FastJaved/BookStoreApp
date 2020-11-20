package com.example.projetkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projetkotlin.MainActivity.Companion.basket
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_book.*

class SingleBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_book)
        setSupportActionBar(toolbar)
        val book = intent.getParcelableExtra<Book>("book")

        val titleView = findViewById<TextView>(R.id.titleView)
        val synopsisView = findViewById<TextView>(R.id.synopsisView)
        val imageView = findViewById<ImageView>(R.id.coverView)
        val priceView = findViewById<TextView>(R.id.priceView)

        if (book != null) {
            titleView.text = book.title
            priceView.text = book.price + " €"
            synopsisView.text = book.synopsis.joinToString()
            Picasso.get().load(book.cover).into(findViewById<ImageView>(R.id.coverView))

        }

        val fab: View = findViewById(R.id.floatingActionButtonAdd)

        fab.setOnClickListener { view ->
            if (book != null) {
                Toast.makeText(this,"Added " + book.title, Toast.LENGTH_SHORT).show()
                basket.books.add(book)
                for (item in basket.books){
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.shop){
            if (basket.books.size > 0){
                val intent = Intent(this@SingleBookActivity,CheckOut::class.java)
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