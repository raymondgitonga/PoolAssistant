package com.tosh.poolassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tosh.poolassistant.R
import com.tosh.poolassistant.view.adapter.OrderAdapter
import com.tosh.poolassistant.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_dash_board.*

class DashBoardFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var ordersListAdapter: OrderAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "Dashboard"

        ordersListAdapter = OrderAdapter(arrayListOf(), context!!)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.refresh()

        ordersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ordersListAdapter
        }

        refreshLayout.setOnRefreshListener{
            ordersList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.orders.observe(viewLifecycleOwner, Observer {
            it?.let {
                ordersList.visibility = View.VISIBLE
                ordersListAdapter.updateOrdersList(it)
            }
        })

        viewModel.loadError.observe(viewLifecycleOwner, Observer {isError->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadingView.visibility  = if(it) View.VISIBLE else View.GONE
                if (it){
                    listError.visibility = View.GONE
                    ordersList.visibility = View.GONE
                }
            }
        })
    }

}
