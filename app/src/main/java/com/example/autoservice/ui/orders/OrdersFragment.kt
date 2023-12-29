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

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!
    private lateinit var parentRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[OrdersViewModel::class.java]

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val modelsList = ArrayList<CurrentOrderViewModel>()
        val currentOrdersList: MutableList<Order> = java.util.ArrayList()
        val newOrdersList = ArrayList<Order>()

        val curOrder = Order("Заказ 1",
            "Иванов Иван Иванович",
            "Волков Владимир Владимирович",
            "Audi Q8",
            "O111OO196",
            "CKO3437483HS",
            "Поменять резину",
            1230.0,
            "Биение в руль",
            "12.02.2023",
            "13.02.2023",
            "Комментарий")
        currentOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)

        newOrdersList.add(curOrder)
        newOrdersList.add(curOrder)
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        modelsList.add(CurrentOrderViewModel(curOrder))
        parentRecyclerView = binding.parentRecyclerView
        parentRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapterCurrentOrders = OrderAdapter(modelsList)
        parentRecyclerView.adapter = adapterCurrentOrders
        
        val adapterNewOrders = context?.let { NewOrdersListAdapter(it, newOrdersList) }
        binding.newOrdersList.adapter = adapterNewOrders
        binding.newOrdersCountText.text = newOrdersList.size.toString()

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

        binding.completedOrdersList.adapter =
            context?.let { CompletedOrdersListAdapter(it, newOrdersList) }
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