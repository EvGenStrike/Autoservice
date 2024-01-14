package com.example.autoservice.ui.orders

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
import com.example.autoservice.databinding.FragmentProfileSkillsBinding
import com.example.autoservice.databinding.FragmentReportBinding
import com.example.autoservice.ui.orders.SkillsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val reportButton = binding.reportCard
        val root: View = binding.root

        // Включение кнопки "назад" в тулбаре
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);
        reportButton.setOnClickListener {
            val visibilityExpandableLayout =
                if (binding.reportTextExpandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            binding.reportTextExpandableLayout.visibility = visibilityExpandableLayout
        }
        binding.reportTextDescription.text = arguments?.getString("report")
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


}