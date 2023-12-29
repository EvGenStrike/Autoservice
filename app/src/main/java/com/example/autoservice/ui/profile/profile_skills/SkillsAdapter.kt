package com.example.autoservice.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.R
import com.example.autoservice.ui.profile.profile_skills.Skill

class SkillsAdapter(
    private val context: Context,
    private val skillsList: ArrayList<Skill>,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(skill: Skill)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_profile_skills_item, parent, false)
        return SkillsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        val skill = skillsList[position]
        holder.bind(skill)

        holder.cardView.setOnClickListener {
            val visibilityExpandableLayout =
                if (holder.expandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            holder.expandableLayout.visibility = visibilityExpandableLayout

            // Вызываем onItemClick, если слушатель установлен
            onItemClickListener?.onItemClick(skill)
        }
    }

    override fun getItemCount(): Int {
        return skillsList.size
    }

    inner class SkillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillNameTextView: TextView = itemView.findViewById(R.id.skills_card_view_skill_name)
        val skillDescriptionTextView: TextView = itemView.findViewById(R.id.skills_card_view_skill_description)
        val cardView: CardView = itemView.findViewById(R.id.skill_card_view)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.profile_fragment_skills_expandable_layout)

        fun bind(skill: Skill) {
            skillNameTextView.text = skill.skillName
            skillDescriptionTextView.text = skill.skillDescription

            setupMaterialCardView(itemView as FrameLayout)
        }
    }

    private fun setupMaterialCardView(frameLayout: FrameLayout){
        val cardView: CardView =
            frameLayout.findViewById(R.id.skill_card_view)
        val expandableLayout: LinearLayout = frameLayout.findViewById(R.id.profile_fragment_skills_expandable_layout)
        cardView.setOnClickListener {
            val visibilityExpandableLayout =
                if (expandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            expandableLayout.visibility = visibilityExpandableLayout
        }
    }
}