package com.example.ctrl_c.ui.transactions.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ctrl_c.R
import com.example.ctrl_c.model.response.userOrder.OrdersItem

class UserOrdersAdapter : RecyclerView.Adapter<UserOrdersAdapter.ListViewHolder>() {
    private var currentList: List<OrdersItem> = emptyList()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStatus: TextView = itemView.findViewById(R.id.tv_transaction_status)
        val tvShopLocation: TextView = itemView.findViewById(R.id.tv_user_transaction_shop_location)
        val tvDate: TextView = itemView.findViewById(R.id.tv_user_transaction_date)
        val tvTime: TextView = itemView.findViewById(R.id.tv_user_transaction_time)
        val tvListOrder: TextView = itemView.findViewById(R.id.tv_user_transaction_list_order)
        val tvListPrice: TextView = itemView.findViewById(R.id.tv_user_transaction_list_price)
        val tvTotalPrice: TextView = itemView.findViewById(R.id.tv_user_transaction_total_price)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val orderStatus: String = currentList[position].status
        val shopLocation = currentList[position].kedai.name
        val orderDate = currentList[position].date
        val orderTime = currentList[position].idOrder
        val listOrder = getListOrder(position)
        val listPrice = getListPrice(position)
        val totalPrice = "Total:\t\t\t\tRp." + currentList[position].price.toString() + ".000"

        holder.tvStatus.text = orderStatus
        holder.tvShopLocation.text = shopLocation
        holder.tvDate.text = orderDate
        holder.tvTime.text = orderTime.toString()
        holder.tvListOrder.text = listOrder
        holder.tvListPrice.text = listPrice
        holder.tvTotalPrice.text = totalPrice
    }

    private fun getListPrice(position : Int) : String {
        var stringTemp = ""
        for(orderDetailItem in currentList[position].orderDetail) {
            stringTemp += orderDetailItem.product.price.toString() + ".000\n"
        }
        return stringTemp
    }

    private fun getListOrder(position : Int): String {
        var stringTemp = ""
        for (orderDetailItem in currentList[position].orderDetail) {
            stringTemp += orderDetailItem.amount.toString() + " x " + orderDetailItem.product.name + "\n"
        }
        return stringTemp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_transaction, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOrdersList(orders: List<OrdersItem>) {
        currentList = orders
        notifyDataSetChanged()
    }
    override fun getItemCount() = currentList.size
}
