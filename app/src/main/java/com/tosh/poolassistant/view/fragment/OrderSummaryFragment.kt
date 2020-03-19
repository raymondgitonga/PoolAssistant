package com.tosh.poolassistant.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

import com.tosh.poolassistant.R
import kotlinx.android.synthetic.main.fragment_order_summary.*

class OrderSummaryFragment : Fragment() {

    private var orderNumber: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_order_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderNumber = arguments?.getLong("ORDER_ID")
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
