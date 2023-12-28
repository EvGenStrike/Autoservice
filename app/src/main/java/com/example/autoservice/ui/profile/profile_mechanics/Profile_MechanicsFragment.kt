package com.example.autoservice.ui.profile.profile_skills

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.autoservice.databinding.FragmentProfileMechanicsBinding


class Profile_MechanicsFragment : Fragment() {
    private var _binding: FragmentProfileMechanicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileMechanicsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true);

       // setup()

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun setup(){
//        val newOrders = binding.newOrdersCardView
//        newOrders.setOnClickListener {
//            val visibilityExpandableLayout =
//                if (binding.mechanicsListViewOrder.visibility == View.GONE) View.VISIBLE
//                else View.GONE
//            binding.mechanicsListViewOrder.visibility = visibilityExpandableLayout
//        }
//    }
}