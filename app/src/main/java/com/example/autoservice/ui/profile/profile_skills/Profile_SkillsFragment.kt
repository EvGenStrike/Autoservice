package com.example.autoservice.ui.profile.profile_skills

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.autoservice.databinding.FragmentProfileSkillsBinding

//ПОМЕНЯТЬ ID В XML ДЛЯ НАВЫКОВ И МЕХАНИКОВ, СДЕЛАТЬ LISTVIEW ДЛЯ НАВЫКОВ И МЕХАНИКОВ

class Profile_SkillsFragment : Fragment() {
    private var _binding: FragmentProfileSkillsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSkillsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Включение кнопки "назад" в тулбаре
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);

        setup()

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

    private fun setup(){
        val newOrders = binding.newOrdersCardView
        newOrders.setOnClickListener {
            val visibilityExpandableLayout =
                if (binding.newOrdersExpandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            binding.newOrdersExpandableLayout.visibility = visibilityExpandableLayout
        }
    }
}