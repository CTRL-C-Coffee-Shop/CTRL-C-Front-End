package com.example.ctrl_c.ui.order.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.model.response.product.ProductItem

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ListViewHolder>() {
    private var currentList: List<ProductItem> = emptyList()

    private lateinit var onItemClickCallback: OnItemClickCallBack


    interface OnItemClickCallBack {
        fun onItemClicked(data: ProductItem)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.productName)
        val imgUrl: ImageView = itemView.findViewById(R.id.productImage)
        val tvDesc: TextView = itemView.findViewById(R.id.productDescription)
        val tvPrice: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val productName = currentList[position].name
        val productDesc = currentList[position].description
        val productPrice = currentList[position].price


        holder.tvProductName.text = productName
        holder.tvDesc.text = productDesc
        holder.tvPrice.text = productPrice.toString() + ".000"

        Glide.with(holder.itemView.context)
            .load(currentList[position].url)
            .error(R.drawable.default_menu)
            .into(holder.imgUrl)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(currentList[holder.adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_list, parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount() = currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setProductData(products: List<ProductItem>) {
        currentList = products
        notifyDataSetChanged()
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }
}