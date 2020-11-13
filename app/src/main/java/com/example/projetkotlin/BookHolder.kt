package com.example.projetkotlin

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

//1
class BookHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    //2
    private var view: View = v
    private var book: Book? = null

    //3
    init {
        v.setOnClickListener(this)
    }

    //4
    override fun onClick(v: View) {
        val context = itemView.context
        val showPhotoIntent = Intent(context, MainActivity::class.java)
        //showPhotoIntent.putExtra(BOOK_KEY, book)
        context.startActivity(showPhotoIntent)

    }

    fun bindBook(book: Book) {
        this.book = book
        Picasso.get().load(book.cover).into(itemView.findViewById<ImageView>(R.id.itemImage))
        itemView.findViewById<TextView>(R.id.itemName).text = book.title
        itemView.findViewById<TextView>(R.id.itemPrice).text = book.price
    }


    companion object {
        //5
        private val BOOK_KEY = "BOOK"
    }

}
