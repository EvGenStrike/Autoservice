package com.example.autoservice.ui.mechanics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.example.autoservice.databinding.FragmentMechanicsBinding
import com.example.autoservice.ui.orders.Order


class MechanicsFragment : Fragment() {

    private var _binding: FragmentMechanicsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listViewAdapter: MechanicsExpandableListViewAdapter
    private val mechanicsList: List<Mechanic> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMechanicsBinding.inflate(inflater, container, false)

        setupExpandableListView(binding)

        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupExpandableListView(binding: FragmentMechanicsBinding){
        val mechanicsListView: ExpandableListView = binding.mechanicsListView;

        showList()

        listViewAdapter = MechanicsExpandableListViewAdapter(
            requireContext(),
            mechanicsList)
        mechanicsListView.setAdapter(listViewAdapter)
    }

    private fun showList(){
        addMechanic(Mechanic(
            "Иван", "Иванов", "Иванович",
            "+79001989301", 5,
            listOf(
                Order("Заказ 1",
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
                    "Комментарий",
                    "",
                    5),
                Order("Заказ 2",
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
                    "Комментарий",
                    "",
                    4)
            )))
        addMechanic(Mechanic(
            "Дмитрий", "Мартынов", "Борисович",
            "+793247118503", 3,
            listOf(
                Order("Заказ 1",
                "Мартынов Дмитрий Борисович",
                "Волков Владимир Владимирович",
                "Audi Q8",
                "O111OO196",
                "CKO3437483HS",
                "Поменять резину",
                1230.0,
                "Биение в руль",
                "12.02.2023",
                "13.02.2023",
                "Комментарий",
                    "",
                3),
                Order("Заказ 2",
                    "Мартынов Дмитрий Борисович",
                    "Волков Владимир Владимирович",
                    "Audi Q8",
                    "O111OO196",
                    "CKO3437483HS",
                    "Поменять резину",
                    1230.0,
                    "Биение в руль",
                    "12.02.2023",
                    "13.02.2023",
                    "Комментарий",
                    "",
                    4)
            )))
        addMechanic(Mechanic(
            "Дмитрий", "Соколов", "Борисович",
            "+79385019571", 4,
            listOf(
                Order("Заказ 1",
                    "Соколов Дмитрий Борисович",
                    "Волков Владимир Владимирович",
                    "Audi Q8",
                    "O111OO196",
                    "CKO3437483HS",
                    "Поменять резину",
                    1230.0,
                    "Биение в руль",
                    "12.02.2023",
                    "13.02.2023",
                    "Комментарий",
                    "",
                    3),
                Order("Заказ 2",
                    "Соколов Дмитрий Борисович",
                    "Волков Владимир Владимирович",
                    "Audi Q8",
                    "O111OO196",
                    "CKO3437483HS",
                    "Поменять резину",
                    1230.0,
                    "Биение в руль",
                    "12.02.2023",
                    "13.02.2023",
                    "Комментарий",
                    "",
                    4)
            )))
    }

    private fun addMechanic(mechanic: Mechanic){
        (mechanicsList as ArrayList<Mechanic>).add(mechanic)
    }
}