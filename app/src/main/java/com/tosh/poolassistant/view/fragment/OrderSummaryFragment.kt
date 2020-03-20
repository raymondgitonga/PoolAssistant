package com.tosh.poolassistant.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tosh.poolassistant.R
import com.tosh.poolassistant.view.adapter.OrderSummaryAdapter
import com.tosh.poolassistant.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_order_summary.*

class OrderSummaryFragment : Fragment() {

    private var orderNumber: Long? = null
    private var orderCost: Double? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var orderSummaryAdapter: OrderSummaryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_order_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "Summary"

        orderNumber = arguments?.getLong("ORDER_ID")
        orderCost= arguments?.getDouble("ORDER_COST")
        summaryOrderNo.text = "Order No: $orderNumber"
        summaryOrderTotal.text = "Order Cost: $orderCost"

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initRecyclerView(orderNumber!!)
    }

    private fun initRecyclerView(orderNumber: Long) {
        val linearLayoutManager = LinearLayoutManager(context)

        summaryOrderRv.layoutManager = linearLayoutManager

        viewModel.getSingleOrders(orderNumber).observe(viewLifecycleOwner, Observer {
            summaryProgress.visibility = GONE
            orderSummaryAdapter = OrderSummaryAdapter(it)
            summaryOrderRv.adapter = orderSummaryAdapter
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                val fragmentDashBoard = DashBoardFragment()
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.details_fragment, fragmentDashBoard)
                fragmentTransaction.commit()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}
