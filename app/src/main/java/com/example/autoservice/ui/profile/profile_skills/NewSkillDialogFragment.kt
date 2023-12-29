package com.example.autoservice.ui.profile.profile_skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.autoservice.R


class NewSkillDialogFragment : DialogFragment() {
    var dialogListener: NewSkillDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_skill, container, false)

        setupSaveButton(view)

        return view
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    private fun setupSaveButton(view: View){
        val button: AppCompatButton = view.findViewById(R.id.add_new_skill_save_button)

        button.setOnClickListener {
            val skillNameFromEditText: String =
                view.findViewById<EditText>(R.id.add_new_skill_name).text.toString()
            val skillDescriptionFromEditText: String =
                view.findViewById<EditText>(R.id.add_new_skill_description).text.toString()
            if (!isSkillCorrect(skillNameFromEditText, skillDescriptionFromEditText)){
                return@setOnClickListener
            }
            dialogListener?.onSkillAdded(
                skillName = skillNameFromEditText,
                skillDescription = skillDescriptionFromEditText
            )
            dismiss()
        }
    }

    private fun isSkillCorrect(skillName: String, skillDescription: String): Boolean{
        if (skillName.isEmpty()){
            Toast.makeText(
                requireActivity(), "Название навыка пустое", Toast.LENGTH_SHORT).show()
            return false
        }
        if (skillDescription.isEmpty()){
            Toast.makeText(
                requireActivity(), "Описание навыка пустое", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}