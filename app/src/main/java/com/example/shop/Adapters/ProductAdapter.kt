package com.example.shop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shop.R
import com.example.shop.DataClasses.Product

class ProductAdapter(var products: List<Product>, private val productPressed: ProductPressed) :
    RecyclerView.Adapter<ProductAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.product_item_image)
        val title: TextView = itemView.findViewById(R.id.product_item_title)
        val brand: TextView = itemView.findViewById(R.id.product_item_brand)
        val price: TextView = itemView.findViewById(R.id.product_item_price)
        val rating: TextView = itemView.findViewById(R.id.product_item_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val product = products[position]
        holder.image.load(product.thumbnail)
        holder.brand.text = product.brand
        holder.rating.text = product.rating.toString()
        holder.title.text = product.title
        holder.price.text = "${product.price}$"
        holder.itemView.setOnClickListener {
            productPressed.onPressed(product)
        }
    }

    interface ProductPressed {
        fun onPressed(product: Product)
    }
}