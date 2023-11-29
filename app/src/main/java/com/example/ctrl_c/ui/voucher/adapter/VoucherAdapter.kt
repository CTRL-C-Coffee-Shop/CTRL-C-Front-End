package com.example.ctrl_c.ui.voucher.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.model.response.voucher.VouchersItem

class VoucherAdapter: RecyclerView.Adapter<VoucherAdapter.ListViewHolder>()  {
    private var currentList: List<VouchersItem> = emptyList()
    private lateinit var onItemClickCallback: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data: VouchersItem)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVoucherName: TextView = itemView.findViewById(R.id.tv_voucher_name)
        val tvVoucherImage: ImageView = itemView.findViewById(R.id.iv_voucher_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_vouchers_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val voucherName  = currentList[position].name

        holder.tvVoucherName.text = voucherName
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(currentList[holder.adapterPosition])
        }
        Glide.with(holder.itemView.context)
            .load(R.drawable.voucher2)
            .into(holder.tvVoucherImage)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setVoucherData(vouchers: List<VouchersItem>) {
        currentList = vouchers
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }
}