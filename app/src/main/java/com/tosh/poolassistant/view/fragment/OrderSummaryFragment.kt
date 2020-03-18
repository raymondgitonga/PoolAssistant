package com.tosh.poolassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tosh.poolassistant.R
import kotlinx.android.synthetic.main.fragment_order_summary.*

/**
 * A simple [Fragment] subclass.
 */
class OrderSummaryFragment : Fragment() {

    private var orderNumber: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_order_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderNumber = arguments?.getInt("ORDER_ID")
    }

}
