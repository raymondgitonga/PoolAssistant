package com.tosh.poolassistant.view.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tosh.poolassistant.R
import com.tosh.poolassistant.model.Order
import com.tosh.poolassistant.view.fragment.OrderDetailsFragment
import com.tosh.poolassistant.view.fragment.OrderSummaryFragment
import com.tosh.poolassistant.view.fragment.RiderDetailsFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OrderAdapter(private val orderList: ArrayList<Order>, val context: Context) :
    RecyclerView.Adapter<OrderAdapter.OrderView>() {

    fun updateOrdersList(newOrdersList: List<Order>) {
        orderList.clear()
        orderList.addAll(newOrdersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_order, parent, false)

        return OrderView(view, orderList)
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: OrderView, position: Int) {
        val order = orderList[position]
        val date = order.createdDate
        val itemId = 0

        val formatDate: Date = SimpleDateFormat("yyyyMMddHHmmss").parse(date)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = targetFormat.format(formatDate)

        holder.deliverDate.text = formattedDate
        holder.orderNumber.text = "Order number: ${order.orderNumber}"
        holder.orderCost.text = "Cost: ${order.cost}"
        holder.quantity.text = "Item quantity: ${order.cartItems.size}"

        if (order.state == "pending"){
            holder.orderStatus.text = order.state
        }else if (order.state == "finalize"){
            holder.orderStatus.text = "finalize"
            holder.rowOrderLl.setBackgroundColor(Color.parseColor("#FF6347"))
        }else{
            holder.rowOrderLl.setBackgroundColor(Color.parseColor("#52e382"))
            holder.orderStatus.text = "complete"
        }

        holder.itemView.setOnClickListener {
            if (order.state == "pending") {
                val fragmentOrderDetails = OrderDetailsFragment()
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                val bundle = Bundle()
                bundle.putLong("ORDER_ID", order.orderNumber)
                bundle.putDouble("ORDER_COST", order.cost)
                fragmentOrderDetails.arguments = bundle
                fragmentTransaction.replace(R.id.details_fragment, fragmentOrderDetails)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }else if(order.state == "transit"){
                val fragmentOrderSummary = OrderSummaryFragment()
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                val bundle = Bundle()
                bundle.putLong("ORDER_ID", order.orderNumber)
                fragmentOrderSummary.arguments = bundle
                fragmentTransaction.replace(R.id.details_fragment, fragmentOrderSummary)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }else{
                val fragmentRiderDetails = RiderDetailsFragment()
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                val bundle = Bundle()
                bundle.putLong("ORDER_ID", order.orderNumber)
                bundle.putDouble("ORDER_COST" ,order.cost)
                fragmentRiderDetails.arguments = bundle
                fragmentTransaction.replace(R.id.details_fragment, fragmentRiderDetails)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    class OrderView(itemView: View, var order: List<Order>) : RecyclerView.ViewHolder(itemView) {
        val orderNumber = itemView.findViewById<TextView>(R.id.orderNumber)
        val deliverDate = itemView.findViewById<TextView>(R.id.deliverDate)
        val orderCost = itemView.findViewById<TextView>(R.id.orderCost)
        val quantity = itemView.findViewById<TextView>(R.id.quantity)
        val orderStatus = itemView.findViewById<TextView>(R.id.orderStatus)
        val rowOrderLl = itemView.findViewById<RelativeLayout>(R.id.rowOrderLl)

    }

}