package com.example.projetkotlin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val books: ArrayList<Book>)  : RecyclerView.Adapter<BookHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return BookHolder(inflatedView)

    }

    override fun getItemCount() = books.size


    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val itemBook = books[position]
        holder.bindBook(itemBook)

    }
}
