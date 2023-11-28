package com.example.ctrl_c.ui.order.checkout.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.model.response.cart.CartItem

class OrderCheckoutAdapter : RecyclerView.Adapter<OrderCheckoutAdapter.ListViewHolder>() {
    private var currentList: List<CartItem> = emptyList()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_product_name_order_checkout)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_product_desc_order_checkout)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_product_price_order_checkout)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_product_amount_order_checkout)
        val ivImage: ImageView = itemView.findViewById(R.id.tv_product_image_order_checkout)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val productName = currentList[position].product.name
        val productDesc = currentList[position].product.description
        val productPrice = currentList[position].product.price
        val productAmount = currentList[position].amount

        holder.tvName.text = productName
        holder.tvDesc.text = productDesc
        holder.tvPrice.text = productPrice.toString()
        holder.tvAmount.text = productAmount.toString()

        Glide.with(holder.itemView.context)
            .load(currentList[position].product.url)
            .error(R.drawable.default_menu)
            .into(holder.ivImage)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCartData(carts: List<CartItem>) {
        currentList = carts
        notifyDataSetChanged()
    }
}