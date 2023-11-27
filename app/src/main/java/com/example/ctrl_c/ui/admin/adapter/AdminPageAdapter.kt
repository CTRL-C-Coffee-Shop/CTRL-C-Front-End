package com.example.ctrl_c.ui.admin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ctrl_c.R
import com.example.ctrl_c.model.response.order.OrdersItem
import com.example.ctrl_c.model.response.product.ProductItem
import com.example.ctrl_c.ui.order.adapter.ProductAdapter


class AdminPageAdapter : RecyclerView.Adapter<AdminPageAdapter.ListViewHolder>() {

    private var currentList: List<OrdersItem> = emptyList()
    private lateinit var onItemClickCallback: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data: OrdersItem)
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderID: TextView = itemView.findViewById(R.id.orderID)
        val tvOrderDate: TextView = itemView.findViewById(R.id.orderDate)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.orderStatus)
        val tvOrderTotalPrice: TextView = itemView.findViewById(R.id.orderTotalPrice)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_status_admin_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val orderID = currentList[position].idOrder
        val orderDate = currentList[position].date
        val orderStatus = currentList[position].status
        val orderPrice = currentList[position].price

        holder.tvOrderID.text = orderID.toString()
        holder.tvOrderDate.text = orderDate
        holder.tvOrderStatus.text = orderStatus
        holder.tvOrderTotalPrice.text = "Rp.$orderPrice.000"

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(currentList[holder.adapterPosition])
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOrderList(orders: List<OrdersItem>) {
        currentList = orders
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }


}