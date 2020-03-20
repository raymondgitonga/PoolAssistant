package com.tosh.poolassistant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tosh.poolassistant.R
import com.tosh.poolassistant.model.CartItem

class OrderSummaryAdapter (private val orderModel: List<CartItem>) : RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryView{
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_summary_details, parent, false)

        return OrderSummaryView(view, orderModel)
    }

    override fun getItemCount() = orderModel.size


    override fun onBindViewHolder(holder: OrderSummaryView, position: Int) {
        val cart = orderModel[position]

        holder.productName.text = cart.productName
        holder.productExtra.text = cart.extraName
        holder.productPrice.text = cart.productPrice.toString()
        holder.productStore.text = cart.vendorName


    }

    class OrderSummaryView(itemView: View, var order: List<CartItem>) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productExtra: TextView = itemView.findViewById(R.id.productExtra)
        val productStore: TextView = itemView.findViewById(R.id.productStore)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)

    }


}