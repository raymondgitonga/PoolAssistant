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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tosh.poolassistant.R
import com.tosh.poolassistant.view.activity.MainActivity
import com.tosh.poolassistant.view.adapter.OrderDetailsAdapter
import com.tosh.poolassistant.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_dash_board.*
import kotlinx.android.synthetic.main.fragment_order_details.*
import kotlinx.coroutines.launch

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

        activity!!.title = "Order"

        orderNumber = arguments?.getLong("ORDER_ID")
        cost = arguments?.getDouble("ORDER_COST")

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initRecyclerView(orderNumber!!, cost!!)

        observeViewModel()
        initRiderDetailsFragment(orderNumber!!, cost!!)


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

    private fun initRiderDetailsFragment(orderNumber: Long, orderCost:Double) {
        completeOrder.setOnClickListener {
            val emptyDialog = MaterialAlertDialogBuilder(context)
                .setTitle("Proceed")
                .setMessage("Are you sure you have collected everything ?")
            emptyDialog.setPositiveButton("Yes") { _, _ ->
                val fragmentRiderDetails = RiderDetailsFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                val bundle = Bundle()
                bundle.putLong("ORDER_ID", orderNumber)
                bundle.putDouble("ORDER_COST", orderCost)
                fragmentRiderDetails.arguments = bundle
                fragmentTransaction.replace(R.id.details_fragment, fragmentRiderDetails)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
                .setNegativeButton("No") { _, _ ->
                }

                .show()
        }
    }
}
