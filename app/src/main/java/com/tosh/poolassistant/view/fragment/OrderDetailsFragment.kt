package com.tosh.poolassistant.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tosh.poolassistant.R
import com.tosh.poolassistant.view.adapter.OrderDetailsAdapter
import com.tosh.poolassistant.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_dash_board.*
import kotlinx.android.synthetic.main.fragment_order_details.*

class OrderDetailsFragment : Fragment() {

    private var orderNumber: Long? = null
    private var cost: Double? = null

    private lateinit var viewModel: MainViewModel
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderNumber = arguments?.getLong("ORDER_ID")
        cost = arguments?.getDouble("ORDER_COST")

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initRecyclerView(orderNumber!!, cost!!)


    }

    private fun initRecyclerView(orderNumber: Long, orderCost: Double) {
        val linearLayoutManager = LinearLayoutManager(context)

        orderDetailsRv.layoutManager = linearLayoutManager

        viewModel.getSingleOrders(orderNumber).observe(viewLifecycleOwner, Observer {
            orderNumberTV.text = "Order Number ${orderNumber}"
            orderTotal.text = "Cost ${orderCost}"
            completeOrder.visibility = VISIBLE
            orderDetailsAdapter = OrderDetailsAdapter(it)
            orderDetailsRv.adapter = orderDetailsAdapter
        })

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.loadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                errorDetails.visibility = if (it) VISIBLE else GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                progressDetails.visibility = if (it) View.VISIBLE else GONE
                if (it) {
                    errorDetails.visibility = GONE
                }
            }
        })
    }


}
