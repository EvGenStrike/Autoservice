package com.example.autoservice.ui.profile.profile_skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.R
import com.example.autoservice.databinding.FragmentProfileSkillsBinding
import com.example.autoservice.ui.orders.SkillsAdapter

//ПОМЕНЯТЬ ID В XML ДЛЯ НАВЫКОВ И МЕХАНИКОВ, СДЕЛАТЬ LISTVIEW ДЛЯ НАВЫКОВ И МЕХАНИКОВ

class Profile_SkillsFragment : Fragment(), AdapterView.OnItemClickListener{
    private var _binding: FragmentProfileSkillsBinding? = null
    private val binding get() = _binding!!

    private lateinit var skillsList: List<Skill>

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

        setupSkillsListView()
        setupFab()

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

    private fun setupSkillsListView(){
        val skillsListView: ListView = binding.profileFragmentSkillsListView;
        skillsList = listOf(
            Skill(
            "Краска машины",
            "Умею быстро красить машину, используя минимум краски"
            ),
            Skill(
                "Стёкла",
                "Могу быстро заменить стёкла"
            ))

        val listViewAdapter = SkillsAdapter(
            requireContext(),
            skillsList)
        skillsListView.adapter = listViewAdapter
        skillsListView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val skill: Skill = skillsList.get(position)
        Toast.makeText(
            requireActivity(), "Нажал на ${skill.skillName}", Toast.LENGTH_SHORT).show()
    }

    private fun setupFab(){

    }
}