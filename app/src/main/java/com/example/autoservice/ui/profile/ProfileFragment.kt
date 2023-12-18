package com.example.autoservice.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.autoservice.databinding.FragmentMechanicsBinding
import com.example.autoservice.databinding.FragmentOrdersBinding
import com.example.autoservice.databinding.FragmentProfileBinding
import com.example.autoservice.ui.mechanics.MechanicsExpandableListViewAdapter
import com.example.autoservice.ui.orders.CurrentOrderViewModel
import com.example.autoservice.ui.orders.Order
import com.example.autoservice.ui.orders.OrderAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}