package com.example.autoservice.ui.profile.profile_skills

import android.os.Bundle
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
import com.example.autoservice.ui.orders.Order
import com.example.autoservice.ui.orders.Profile_OrderAdapter
import com.example.autoservice.ui.profile.profile_mechanics.MechanicDialogListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

//ПОМЕНЯТЬ ID В XML ДЛЯ НАВЫКОВ И МЕХАНИКОВ, СДЕЛАТЬ LISTVIEW ДЛЯ НАВЫКОВ И МЕХАНИКОВ

class Profile_MechanicsFragment : Fragment(), AdapterView.OnItemClickListener,
    MechanicDialogListener {
    private var _binding: FragmentProfileMechanicsBinding? = null
    private val binding get() = _binding!!

    private var ordersList: ArrayList<Order> = fillList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMechanicsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Включение кнопки "назад" в тулбаре
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);

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
            ordersList,
            object : Profile_OrderAdapter.OnItemClickListener {
                override fun onItemClick(order: Order) {
                    //реализация клика на элемент
                }
            }
        )
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

    private fun fillList(): ArrayList<Order>{
        return arrayListOf(
            Order(
                "Заказ 1",
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
                "Комментарий"
            ),
            Order(
                "Заказ 2",
                "Мартынов Дмитрий Борисович",
                "Волков Владимир Владимирович",
                "Audi Q8",
                "O111OO196",
                "CKO3437483HS",
                "Поменять резину",
                1230.0,
                "Вмятина",
                "12.02.2023",
                "13.02.2023",
                "Комментарий",
                4
            ),
            Order(
                "Заказ 3",
                "Соколов Дмитрий Борисович",
                "Волков Владимир Владимирович",
                "Audi Q8",
                "O111OO196",
                "CKO3437483HS",
                "Поменять резину",
                1230.0,
                "Глохнет двигатель",
                "12.02.2023",
                "13.02.2023",
                "Комментарий",
                3
            )
        )
    }

}