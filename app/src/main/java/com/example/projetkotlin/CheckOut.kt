package com.example.projetkotlin

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_check_out.*

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class CheckOut : AppCompatActivity() {
    private lateinit var listView:ListView
    private lateinit var gridLayoutManager: GridLayoutManager
    private var listOfferX: ArrayList<Offer> =  arrayListOf<Offer>()

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
        apiCall.enqueue(object: Callback<Offer> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Offer>, response: Response<Offer>) {
                val offer: Offer? = response.body()
                var total:Int = 0

                Log.d("lst", offer.toString())
                if (offer != null) {
                    Log.d("t", offer.offers[0].type)
                }
                var offerWithPercentage = OfferX(0,"",0)
                if (offer != null) {
                    for (off in offer.offers){
                        if (off.type == "percentage"){
                            offerWithPercentage = off
                        }
                    }
                }
                for (item in basket.books){
                    total += item.price.toInt()
                }
                val totalPriceView: TextView = findViewById(R.id.totaltextView)
                val discountTextView: TextView = findViewById(R.id.discountTextView)
                val finalTotal:Float = Math.round((total.toFloat()  / (1.0 + (offerWithPercentage.value.toFloat()/100.0)))*100.0)/100.0.toFloat()


                totaltextView.text = "TOTAL : $total €"
                discountTextView.text = "DISCOUNT : ${offerWithPercentage.value.toString()} %"
                finalTotalText.text = "FINAL : ${finalTotal.toString()} €"


            }

            override fun onFailure(call: Call<Offer>, t: Throwable) {
                Log.e("Offer", "Error : $t")
            }
        })

       fab.setOnClickListener { view ->
                showDialog()
            }
        }

    private fun showDialog(){
        lateinit var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Payment")
        builder.setMessage("Do you want to pay ?")

        val basket = intent.getParcelableExtra<Basket>("basket")

        val intent = Intent(this@CheckOut,MainActivity::class.java)

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> startActivity(intent)
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(this, "No button clicked.", Toast.LENGTH_SHORT).show()
                DialogInterface.BUTTON_NEUTRAL -> Toast.makeText(this, "Cancel.", Toast.LENGTH_SHORT).show()

            }
        }


        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("CANCEL",dialogClickListener)

        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()

    }

    }