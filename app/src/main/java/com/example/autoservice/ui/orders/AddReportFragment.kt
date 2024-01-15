package com.example.autoservice.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.autoservice.databinding.FragmentAddReportBinding
import com.example.autoservice.databinding.FragmentReportBinding
import com.google.firebase.database.FirebaseDatabase

class AddReportFragment: Fragment() {
    private var _binding: FragmentAddReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Включение кнопки "назад" в тулбаре
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);
        val text: EditText = binding.newReport
        val buttonAdd: AppCompatButton = binding.createTextToReportButton
        buttonAdd.setOnClickListener {
            arguments?.getString("order")
                ?.let { it1 -> onResponsibleSelected(it1, text.text.toString()) }
        }
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
    fun onResponsibleSelected(orderName: String, text: String) {
        val orderRef = FirebaseDatabase.getInstance()
            .reference.child("Order").child(orderName)

        // Update the responsible mechanic field
        orderRef.child("responsibleName").setValue(text)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Новый отчёт создан", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "Произошла ошибка", Toast.LENGTH_SHORT).show()
            }
    }
}