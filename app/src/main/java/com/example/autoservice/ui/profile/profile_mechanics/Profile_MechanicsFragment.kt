package com.example.autoservice.ui.profile.profile_skills

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.databinding.FragmentProfileMechanicsBinding
import com.example.autoservice.ui.mechanics.Mechanic
import com.example.autoservice.ui.orders.Order
import com.example.autoservice.ui.orders.Profile_OrderAdapter
import com.example.autoservice.ui.profile.profile_mechanics.MechanicDialogListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.atomic.AtomicBoolean


//ПОМЕНЯТЬ ID В XML ДЛЯ НАВЫКОВ И МЕХАНИКОВ, СДЕЛАТЬ LISTVIEW ДЛЯ НАВЫКОВ И МЕХАНИКОВ

class Profile_MechanicsFragment : Fragment(), AdapterView.OnItemClickListener,
    MechanicDialogListener, Profile_OrderAdapter.OnResponsibleSelectedListener {
    private var _binding: FragmentProfileMechanicsBinding? = null
    private val binding get() = _binding!!

    private val mechanicsList: List<Mechanic> = ArrayList()
    private val ordersList: List<Order> = ArrayList()

    private val mechanicsHashMap: HashMap<String, Mechanic> = HashMap()
    private val ordersHashMap: HashMap<String, Order> = HashMap()

    val done = AtomicBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getMechanics()
        getOrders()

        _binding = FragmentProfileMechanicsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Включение кнопки "назад" в тулбаре
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);

//        getOrders(){
//            getMechanics()
//        }

//        getMechanics(){
//            getOrders()
//        }

        setupOrdersRecyclerView()

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupOrdersRecyclerView() {
        updateSkills()
    }

    override fun onItemClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        val order: Order = ordersList[position]
        Toast.makeText(
            requireActivity(), "Нажал на ${order.orderName}", Toast.LENGTH_SHORT
        ).show()
    }

    fun updateSkills() {
        val ordersRecyclerView: RecyclerView = binding.profileFragmentOrdersRecyclerView

        val recyclerViewAdapter = Profile_OrderAdapter(
            requireContext(),
            ordersList as ArrayList<Order>,
            mechanicsList as ArrayList<Mechanic>,
            object : Profile_OrderAdapter.OnItemClickListener {
                override fun onItemClick(order: Order) {
                    //реализация клика на элемент
                }
            }
        )
        recyclerViewAdapter.setOnResponsibleSelectedListener(this)
        ordersRecyclerView.adapter = recyclerViewAdapter
    }

    private fun showDialogFradment(view: View) {
        val dialogFragment = MechanicDialogFragment()
        dialogFragment.dialogListener = this
        dialogFragment.show(requireActivity().supportFragmentManager, "NewSkillDialogFragment")
    }

    override fun onMechanicDistributed(orderName: String, orderDescription: String) {
        TODO()
    }

    private fun getMechanics() {
        val mechanicsTableRef = FirebaseDatabase.getInstance().reference.child("Mechanics")
        mechanicsTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    requireActivity().runOnUiThread {
                        for (ds in dataSnapshot.children) {
                            val mechanic: Mechanic? = ds.getValue(Mechanic::class.java)
                            if (mechanic != null) {
                                ds.key?.let { mechanicsHashMap.put(it, mechanic) }
                                addMechanic(mechanic)
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

    fun getOrders() {
        val ordersTableRef = FirebaseDatabase.getInstance().reference.child("Order")
        ordersTableRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                requireActivity().runOnUiThread {
                    for (ds in dataSnapshot.children) {
                        val order: Order? = ds.getValue(Order::class.java)
                        ds.key?.let {
                            if (order != null) {
                                order.responsibleName = getMechanicName(order.userId)
                                ordersHashMap.put(it, order)
                            }
                        }
                        addOrder(order)
                    }
                    // После добавления всех данных, обновите RecyclerView или другие компоненты интерфейса
                    updateSkills()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Обработайте ошибку, если это необходимо
                Log.e("Profile_MechanicsFragment", "onCancelled: ${databaseError.message}")
            }
        })
    }

    private fun addMechanic(mechanic: Mechanic?){
        if (mechanic != null) {
            (mechanicsList as ArrayList<Mechanic>).add(mechanic)
        }
    }

    private fun addOrder(order: Order?){
        if (order != null) {
            order.responsibleName = getMechanicName(order.userId)
            (ordersList as ArrayList<Order>).add(order)
        }
    }

    override fun onResponsibleSelected(order: Order, mechanicName: String) {
        // Обновите значение в таблице Order, используя order и mechanicName
        // Например, можно использовать Firebase Database API для этого
        Toast.makeText(requireActivity(), "${order.orderName} ${mechanicName}", Toast.LENGTH_SHORT).show()

        val orderNameInDB = getOrderNameInDB(order)
        val orderRef = FirebaseDatabase.getInstance()
            .reference.child("Order").child(orderNameInDB)

        val userId = getMechanicId(mechanicName)
        // Update the responsible mechanic field
        orderRef.child("userId").setValue(userId)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Механик успешно назначен", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "Произошла ошибка", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getMechanicId(mechanicName: String): String{
        for ((userId, mechanic) in mechanicsHashMap){
            if (mechanic.getFullName() == mechanicName){
                return userId
            }
        }
        return ""
    }

    private fun getMechanicName(mechanicId: String): String{
        for ((userId, mechanic) in mechanicsHashMap){
            if (userId == mechanicId){
                return mechanic.getFullName()
            }
        }
        return ""
    }

    private fun getOrderNameInDB(chosen_order: Order): String{
        for ((orderNameInDB, order) in ordersHashMap){
            if (order.orderName == chosen_order.orderName){
                return orderNameInDB
            }
        }
        return ""
    }
}