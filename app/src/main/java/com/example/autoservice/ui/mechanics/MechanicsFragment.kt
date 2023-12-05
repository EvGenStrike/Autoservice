package com.example.autoservice.ui.mechanics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ExpandableListView.OnGroupClickListener
import android.widget.SimpleExpandableListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.autoservice.databinding.FragmentMechanicsBinding
import com.example.autoservice.entities.Mechanic
import com.example.autoservice.entities.Order


class MechanicsFragment : Fragment() {

    private var _binding: FragmentMechanicsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listViewAdapter: MechanicsExpandableListViewAdapter
    private lateinit var mechanicsList: List<Mechanic>
    private lateinit var ordersMap: HashMap<Mechanic, List<Order>>

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
            mechanicsList,
            ordersMap)
        mechanicsListView.setAdapter(listViewAdapter)
    }

    private fun showList(){
        mechanicsList = ArrayList()
        ordersMap = HashMap()

        addMechanic(Mechanic("Илья", "Обабков", "Николаевич"))

        (mechanicsList as ArrayList<Mechanic>).add(
            Mechanic(
                "Денис", "Шадрин", "Борисович")
        )

        val order1 : MutableList<Order> = ArrayList()
        order1.add(Order("Заказ 1"))
        order1.add(Order("Заказ 2"))

        val order2 : MutableList<Order> = ArrayList()
        order2.add(Order("Заказ 1"))
        order2.add(Order("Заказ 2"))

        ordersMap[mechanicsList[0]] = order1
        ordersMap[mechanicsList[1]] = order2
    }

    private fun addMechanic(mechanic: Mechanic){
        (mechanicsList as ArrayList<Mechanic>).add(mechanic)
    }
}