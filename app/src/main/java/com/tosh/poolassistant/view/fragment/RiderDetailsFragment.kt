package com.tosh.poolassistant.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tosh.poolassistant.R
import kotlinx.android.synthetic.main.fragment_rider_details.*

class RiderDetailsFragment : Fragment() {

    private var orderNumber: Long? = null
    private var cost: Double? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_rider_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderNumber = arguments?.getLong("ORDER_ID")
        cost = arguments?.getDouble("ORDER_COST")

        activity!!.title = "Partner Details"
        callHelpLine()
        initorderSummaryFragment(orderNumber!!, cost!!)
    }

    private fun callHelpLine(){
        val phoneNo = "0729320243"
        callTv.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phoneNo"))
            activity!!.startActivity(intent)
        }
    }

    private fun initorderSummaryFragment(orderNumber: Long, orderCost:Double){
        completeRiderBtn.setOnClickListener {
            val emptyDialog = MaterialAlertDialogBuilder(context)
                .setTitle("Complete")
                .setMessage("Are you sure you want to complete the order")
            emptyDialog.setPositiveButton("Yes") { _, _ ->
                val fragmentOrderSummary = OrderSummaryFragment()
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val bundle = Bundle()
                bundle.putLong("ORDER_ID", orderNumber)
                bundle.putDouble("ORDER_COST", orderCost)
                fragmentOrderSummary.arguments = bundle
                fragmentTransaction.replace(R.id.details_fragment, fragmentOrderSummary)
                fragmentTransaction.commit()
            }
                .setNegativeButton("No") { _, _ ->
                }

                .show()
        }
    }
}
