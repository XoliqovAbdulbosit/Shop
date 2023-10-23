package com.example.shop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.DataClasses.Cart
import com.example.shop.R

class CartAdapter(private var carts: List<Cart>) : RecyclerView.Adapter<CartAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.cart_title)
        val price: TextView = itemView.findViewById(R.id.cart_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val cart = carts[position]
        holder.title.text = cart.title
        holder.price.text = "${cart.price}$"
    }
}