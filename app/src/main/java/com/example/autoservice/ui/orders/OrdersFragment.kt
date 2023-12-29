package com.example.autoservice.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.databinding.FragmentOrdersBinding
import com.example.autoservice.ui.orders.OrderAdapter.OrderViewHolder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.Objects

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var loadText: TextView
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[OrdersViewModel::class.java]

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        parentRecyclerView = binding.parentRecyclerView
        loadText = binding.textLoad
        dbRef = FirebaseDatabase.getInstance().getReference("Orders")
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val modelsList = ArrayList<CurrentOrderViewModel>()
        val newOrdersList = ArrayList<Order>()
        val completedOrdersList = ArrayList<Order>()
        parentRecyclerView.visibility = View.GONE
        val ref = FirebaseDatabase.getInstance().reference.child("Order")
        val adapterNewOrders = context?.let { NewOrdersListAdapter(it, newOrdersList) }
        binding.newOrdersList.adapter = adapterNewOrders
        val adapterCompletedOrders = context?.let { CompletedOrdersListAdapter(it, completedOrdersList) }
        binding.completedOrdersList.adapter = adapterCompletedOrders

        ref.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val order: Order? = ds.getValue(Order::class.java)
                        when (order?.orderType) {
                            OrderType.Current ->  modelsList.add(CurrentOrderViewModel(order))
                            OrderType.New -> newOrdersList.add(order)
                            OrderType.Completed -> completedOrdersList.add(order)
                            else -> {}
                        }

                    }
                    adapterNewOrders?.notifyDataSetChanged()
                    adapterCompletedOrders?.notifyDataSetChanged()

                    binding.newOrdersCountText.text = newOrdersList.size.toString()
                    loadText.visibility = View.INVISIBLE
                    parentRecyclerView.visibility = View.VISIBLE

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
        parentRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapterCurrentOrders = OrderAdapter(modelsList)
        parentRecyclerView.adapter = adapterCurrentOrders

        val newOrders = binding.newOrdersCardView
        newOrders.setOnClickListener {
            val visibilityExpandableLayout =
                if (binding.newOrdersExpandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            val visibilityCountText =
                if (binding.newOrdersExpandableLayout.visibility == View.GONE) View.GONE
                else View.VISIBLE
            binding.newOrdersExpandableLayout.visibility = visibilityExpandableLayout
            binding.newOrdersCountText.visibility = visibilityCountText
        }


        val completedOrders = binding.completedOrdersCardView
        var originalScrollPosition = 0
        completedOrders.setOnClickListener {
            val visibilityExpandableLayout =
                if (binding.completedOrdersExpandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            binding.completedOrdersExpandableLayout.visibility = visibilityExpandableLayout

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}