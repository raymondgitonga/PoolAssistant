package com.tosh.poolassistant.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tosh.poolassistant.model.Order

class OrderDetailsAdapter(private val orderModel: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = orderModel.size

    override fun onBindViewHolder(holder: OrderAdapter.OrderView, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class OrderView(itemView: View, var order: List<Order>) : RecyclerView.ViewHolder(itemView) {

    }
}