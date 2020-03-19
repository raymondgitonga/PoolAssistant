package com.tosh.poolassistant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tosh.poolassistant.R
import com.tosh.poolassistant.model.CartItem

class OrderDetailsAdapter(private val orderModel: List<CartItem>) :
    RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsView {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_order_details, parent, false)

        return OrderDetailsView(view, orderModel)
    }

    override fun getItemCount() = orderModel.size


    override fun onBindViewHolder(holder: OrderDetailsView, position: Int) {
        val cart = orderModel[position]
        holder.productName.text = cart.productName
        holder.productExtra.text = cart.extraName
        holder.productStore.text = cart.vendorName
        holder.productPrice.text = cart.productPrice.toString()
    }

    class OrderDetailsView(itemView: View, var order: List<CartItem>) :
        RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productExtra: TextView = itemView.findViewById(R.id.productExtra)
        val productStore: TextView = itemView.findViewById(R.id.productStore)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val checkProduct: CheckBox = itemView.findViewById(R.id.checkProduct)
    }


}