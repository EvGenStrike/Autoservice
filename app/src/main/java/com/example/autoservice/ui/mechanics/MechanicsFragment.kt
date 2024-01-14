package com.example.autoservice.ui.mechanics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.example.autoservice.databinding.FragmentMechanicsBinding
import com.example.autoservice.ui.orders.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MechanicsFragment : Fragment() {

    private var _binding: FragmentMechanicsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listViewAdapter: MechanicsExpandableListViewAdapter
    private val mechanicsList: List<Mechanic> = ArrayList()
    private val ordersList: List<Order> = ArrayList()
    private lateinit var mechanicsListView: ExpandableListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMechanicsBinding.inflate(inflater, container, false)
        mechanicsListView = binding.mechanicsListView;
        setupExpandableListView(binding)

        val ordersTableRef = FirebaseDatabase.getInstance().reference.child("Order")
        getOrders(ordersTableRef)

        val mechanicsTableRef = FirebaseDatabase.getInstance().reference.child("Mechanics")
        setDBListeners(mechanicsTableRef, listViewAdapter)


        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupExpandableListView(binding: FragmentMechanicsBinding){
        //showList()

        listViewAdapter = MechanicsExpandableListViewAdapter(
            requireContext(),
            mechanicsList)
        mechanicsListView.setAdapter(listViewAdapter)
    }

    private fun setDBListeners(
        mechanicsTableRef: DatabaseReference,
        adapterMechanics: MechanicsExpandableListViewAdapter?,
    ) {
        mechanicsTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val mechanic: Mechanic? = ds.getValue(Mechanic::class.java)
                        fillOrdersById(ds.key, mechanic)
                        addMechanic(mechanic)
                    }
                    updateViewAfterGetValuesFromDB(
                        adapterMechanics
                    )
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

    fun fillOrdersById(userId: String?, mechanic: Mechanic?){
        for (order in ordersList){
            if (userId == order.userId){
                mechanic?.orders?.add(order)
            }
        }
    }

    fun getOrders(
        ordersTableRef: DatabaseReference
    ) {
        ordersTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val order: Order? = ds.getValue(Order::class.java)
                        addOrder(order)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

    private fun updateViewAfterGetValuesFromDB(
        adapterMechanics: MechanicsExpandableListViewAdapter?
    ) {
        adapterMechanics?.notifyDataSetChanged()
    }

    private fun addMechanic(mechanic: Mechanic?){
        if (mechanic != null) {
            (mechanicsList as ArrayList<Mechanic>).add(mechanic)
        }
    }

    private fun addOrder(mechanic: Order?){
        if (mechanic != null) {
            (ordersList as ArrayList<Order>).add(mechanic)
        }
    }
}
