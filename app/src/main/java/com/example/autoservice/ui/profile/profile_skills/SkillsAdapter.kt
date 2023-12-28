package com.example.autoservice.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.autoservice.R
import com.example.autoservice.ui.profile.profile_skills.Skill

class SkillsAdapter(
    private val context: Context,
    private val skillsList: List<Skill>
) : BaseAdapter() {
    private lateinit var skillName: TextView
    private lateinit var skillDescription: TextView
    override fun getCount(): Int {
        return skillsList.size
    }

    override fun getItem(position: Int): Any {
        return skillsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        val skill = getItem(position) as Skill
        val holderView: HolderView

        if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.fragment_profile_skills_item, null)
            holderView = HolderView(convertView)
        }
        else{
            holderView = convertView.tag as HolderView
        }

        setupMaterialCardView(convertView as FrameLayout)

        val skillNameTextView = convertView!!.findViewById<TextView>(R.id.skills_card_view_skill_name)
        skillNameTextView.text = skill.skillName

        val skillDescriptionTextView = convertView.findViewById<TextView>(R.id.skills_card_view_skill_description)
        skillDescriptionTextView.text = skill.skillDescription

        return convertView
    }

    private class HolderView(view: View){
        val skillName: TextView = view.findViewById(R.id.skills_card_view_skill_name)
        val skillDescription: TextView = view.findViewById(R.id.skills_card_view_skill_description)
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