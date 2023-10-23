package com.example.shop.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.example.shop.R

class CategoryAdapter(
    private val categories: List<String>,
    private val context: Context,
    private val categoryPressed: CategoryPressed
) : RecyclerView.Adapter<CategoryAdapter.MyHolder>() {
    var current = 0

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.category)
        val text: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categories.size + 1
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (position == 0) {
            holder.text.text = "All"
        } else {
            holder.text.text = categories[position - 1].capitalize()
        }

        if (current == position) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.black))
            holder.text.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.text.setTextColor(context.resources.getColor(R.color.black))
        }

        holder.cardView.setOnClickListener {
            if (position != current) {
                notifyItemChanged(current)
                current = position
                notifyItemChanged(current)
                if (position == 0) categoryPressed.onPressed("")
                else categoryPressed.onPressed(categories[position - 1])
            }
        }
    }

    interface CategoryPressed {
        fun onPressed(category: String)
    }
}