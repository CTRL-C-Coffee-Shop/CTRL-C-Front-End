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
        val tvName: TextView = itemView.findViewById(R.id.tv_product_name_order_checkout_cart)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_product_desc_order_checkout_cart)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_product_price_order_checkout_cart)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_product_amount_order_checkout_cart)
        val ivImage: ImageView = itemView.findViewById(R.id.tv_product_image_order_checkout_cart)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val productName = currentList[position].product.name
        val productDesc = currentList[position].product.description
        val productPrice = currentList[position].product.price * currentList[position].amount
        val productAmount = currentList[position].amount

        holder.tvName.text = productName
        holder.tvDesc.text = productDesc
        holder.tvPrice.text = "Rp. $productPrice.000"
        holder.tvAmount.text = "${productAmount}x"

        Glide.with(holder.itemView.context)
            .load(currentList[position].product.url)
            .error(R.drawable.default_menu)
            .into(holder.ivImage)

    }

    fun getTotalPrice(): Int {
        var totalPrice = 0
        for (cartItem in currentList) {
            val productPrice = cartItem.product.price * cartItem.amount
            totalPrice += productPrice
        }
        return totalPrice
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_order_checkout, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCartData(carts: List<CartItem>) {
        currentList = carts
        notifyDataSetChanged()
    }
}