package com.example.autoservice.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.databinding.FragmentOrdersBinding
import com.example.autoservice.ui.mechanics.Mechanic
import com.example.autoservice.ui.mechanics.MechanicsExpandableListViewAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.Dictionary
import java.util.Objects

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var loadText: TextView
    private lateinit var dbRef: DatabaseReference
    private lateinit var mechanicsDict: HashMap<String?, Mechanic?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setFields(inflater, container)
        setExpandableViewOnOrders(binding)
        val root: View = binding.root
        val ordersTableRef = FirebaseDatabase.getInstance().reference.child("Order")
        val mechanicsTableRef = FirebaseDatabase.getInstance().reference.child("Mechanics")

        val modelsCurrentOrdersList = ArrayList<CurrentOrderViewModel>()
        val newOrdersList = ArrayList<Order>()
        val completedOrdersList = ArrayList<Order>()

        val (adapterNewOrders, adapterCompletedOrders) = setAdapters(
            newOrdersList,
            completedOrdersList,
            modelsCurrentOrdersList
        )
        fillMechanics(mechanicsTableRef) {
            setDBListeners(
                ordersTableRef,
                modelsCurrentOrdersList,
                newOrdersList,
                completedOrdersList,
                adapterNewOrders,
                adapterCompletedOrders
            )
        }

        return root
    }

    private fun fillMechanics(
        mechanicsTableRef: DatabaseReference,
        callback: () -> Unit
    ) {
        mechanicsDict = HashMap()
        mechanicsTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val mechanic: Mechanic? = ds.getValue(Mechanic::class.java)
                        mechanicsDict[ds.key] = mechanic
                    }
                    // Вызовем callback после завершения получения данных
                    callback.invoke()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибок
                }
            })
    }

    fun setExpandableViewOnOrders(
        binding: FragmentOrdersBinding
    ) {
        val completedOrders = binding.completedOrdersCardView
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

        completedOrders.setOnClickListener {
            binding.ordersMainScrollView.post {
                binding.ordersMainScrollView.fullScroll(View.FOCUS_DOWN)
            }
            val visibilityExpandableLayout =
                if (binding.completedOrdersExpandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            binding.completedOrdersExpandableLayout.visibility = visibilityExpandableLayout
        }
    }

    fun setDBListeners(
        ordersTableRef: DatabaseReference,
        modelsCurrentOrdersList: ArrayList<CurrentOrderViewModel>,
        newOrdersList: ArrayList<Order>,
        completedOrdersList: ArrayList<Order>,
        adapterNewOrders: NewOrdersListAdapter?,
        adapterCompletedOrders: CompletedOrdersListAdapter?
    ) {
        ordersTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val order: Order? = ds.getValue(Order::class.java)
                        for (mechanic in mechanicsDict) {
                            if (order?.userId == mechanic.key) {
                                order?.responsibleName = mechanic.value?.getFullName().toString()
                            }
                        }
                        when (order?.orderType) {
                            OrderType.Current -> modelsCurrentOrdersList.add(
                                CurrentOrderViewModel(
                                    order
                                )
                            )
                            OrderType.New -> newOrdersList.add(order)
                            OrderType.Completed -> completedOrdersList.add(order)
                            else -> {}
                        }

                    }
                    updateViewAfterGetValuesFromDB(
                        adapterNewOrders,
                        adapterCompletedOrders,
                        newOrdersList
                    )
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

    fun updateViewAfterGetValuesFromDB(
        adapterNewOrders: NewOrdersListAdapter?,
        adapterCompletedOrders: CompletedOrdersListAdapter?,
        newOrdersList: ArrayList<Order>
    ) {
        adapterNewOrders?.notifyDataSetChanged()
        adapterCompletedOrders?.notifyDataSetChanged()
        parentRecyclerView.adapter?.notifyDataSetChanged()

        binding.newOrdersCountText.text = newOrdersList.size.toString()
        loadText.visibility = View.INVISIBLE
        parentRecyclerView.visibility = View.VISIBLE
        binding.completedOrdersCardView.visibility = View.VISIBLE
        binding.newOrdersCardView.visibility = View.VISIBLE
    }

    private fun setAdapters(
        newOrdersList: ArrayList<Order>,
        completedOrdersList: ArrayList<Order>,
        modelsCurrentOrdersList: ArrayList<CurrentOrderViewModel>
    ): Pair<NewOrdersListAdapter?, CompletedOrdersListAdapter?> {
        val adapterNewOrders = context?.let { NewOrdersListAdapter(it, newOrdersList) }
        val adapterCompletedOrders =
            context?.let { CompletedOrdersListAdapter(it, completedOrdersList) }
        parentRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapterCurrentOrders = OrderAdapter(modelsCurrentOrdersList)
        parentRecyclerView.adapter = adapterCurrentOrders

        binding.newOrdersList.adapter = adapterNewOrders
        binding.completedOrdersList.adapter = adapterCompletedOrders
        return Pair(adapterNewOrders, adapterCompletedOrders)
    }

    private fun setFields(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        parentRecyclerView = binding.parentRecyclerView
        loadText = binding.textLoad
        dbRef = FirebaseDatabase.getInstance().getReference("Orders")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}