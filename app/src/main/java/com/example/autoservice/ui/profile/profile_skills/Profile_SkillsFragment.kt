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
import com.example.autoservice.databinding.FragmentProfileSkillsBinding
import com.example.autoservice.ui.mechanics.Mechanic
import com.example.autoservice.ui.orders.Order
import com.example.autoservice.ui.orders.SkillsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Profile_SkillsFragment : Fragment(), AdapterView.OnItemClickListener, NewSkillDialogListener{
    private var _binding: FragmentProfileSkillsBinding? = null
    private val binding get() = _binding!!

    private var ordersList: ArrayList<Order> = ArrayList()
    private var mechanicsList: ArrayList<Mechanic> = ArrayList()

    private var skillsList: ArrayList<Skill> = arrayListOf(
    Skill("Краска машины", "Умею быстро красить машину, используя минимум краски"),
    Skill("Стёкла", "Могу быстро заменить стёкла")
    )

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

        getMechanics()
        getOrders()

        setupSkillsRecyclerView()
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

    private fun setupSkillsRecyclerView() {
        updateSkills()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val skill: Skill = skillsList.get(position)
        Toast.makeText(
            requireActivity(), "Нажал на ${skill.skillName}", Toast.LENGTH_SHORT).show()
    }

    fun updateSkills(){
        val skillsRecyclerView: RecyclerView = binding.profileFragmentSkillsRecyclerView

        val recyclerViewAdapter = SkillsAdapter(
            requireContext(),
            skillsList,
            object : SkillsAdapter.OnItemClickListener {
                override fun onItemClick(skill: Skill) {
                    //реализация клика на элемент
                }
            }
        )
        skillsRecyclerView.adapter = recyclerViewAdapter
    }

    private fun setupFab(){
        val fab: FloatingActionButton = binding.skillsNewFab
        fab.setOnClickListener{
            showDialogFradment(fab)
        }
    }

    private fun showDialogFradment(view: View) {
        val dialogFragment = NewSkillDialogFragment()
        dialogFragment.dialogListener = this
        dialogFragment.show(requireActivity().supportFragmentManager, "NewSkillDialogFragment")
    }

    override fun onSkillAdded(skillName: String, skillDescription: String) {
        skillsList.add(Skill(skillName, skillDescription))
        updateSkills()
    }

    private fun getMechanics() {
        val mechanicsTableRef = FirebaseDatabase.getInstance().reference.child("Mechanics")
        mechanicsTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val mechanic: Mechanic? = ds.getValue(Mechanic::class.java)
                        if (mechanic != null) {
                            mechanicsList.add(mechanic)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

    fun getOrders() {
        val ordersTableRef = FirebaseDatabase.getInstance().reference.child("Orders")
        ordersTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val order: Order? = ds.getValue(Order::class.java)
                        if (order != null) {
                            ordersList.add(order)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

}