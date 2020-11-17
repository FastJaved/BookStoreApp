package com.example.projetkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.text.FieldPosition

class ListViewAdapter(private val context: Context, private val dataSource: ArrayList<Book>) : BaseAdapter() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.listview_item_row, parent, false)

        val titleTextView = rowView.findViewById<TextView>(R.id.itemNameShop)
        val priceView = rowView.findViewById<TextView>(R.id.itemPriceShop)
        val imgView = rowView.findViewById<ImageView>(R.id.itemImageShop)

        val book = getItem(position) as Book
        titleTextView.text = book.title
        priceView.text = book.price + " €"

        Picasso.get().load(book.cover).into(imgView.findViewById<ImageView>(R.id.itemImageShop))

        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}