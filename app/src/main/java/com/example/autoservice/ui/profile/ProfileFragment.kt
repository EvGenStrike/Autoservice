package com.example.autoservice.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.autoservice.MainActivity
import com.example.autoservice.R
import com.example.autoservice.Registration
import com.example.autoservice.User
import com.example.autoservice.databinding.FragmentProfileBinding
import com.example.autoservice.ui.orders.Order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException


class ProfileFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageView: ImageView

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var user: User
    private lateinit var transparentBackground: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val usersTableRef = FirebaseDatabase.getInstance().reference.child("Users")

        val userNameTextView = binding.profileFragmentProfileName
        setupUserName(userNameTextView, usersTableRef)
        transparentBackground = binding.profileFragmentBackround

        val profileImageView = binding.profileFragmentProfilePicture
        imageView = profileImageView
        setImageViewOnClick(binding.profileFragmentProfilePicture)
        setListView(binding.profileFragmentAvailableButtonsListView)

        val logoutButton = binding.profileFragmentLogoutButton
        setupLogoutButton(logoutButton)

        return root
    }

    private fun setupLogoutButton(logoutButton: AppCompatButton) {
        logoutButton.setOnClickListener {
            deleteUserToken()
            goBackToAuth()
        }
    }

    private fun deleteUserToken() {
        // Сохранение токена в SharedPreferences или другом месте для последующего использования
        val sharedPreferences = requireContext()
            .getSharedPreferences("user_prefs", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun goBackToAuth(){
        val intent = Intent(requireContext(), Registration::class.java)
        startActivity(intent)
    }

    fun setupUserName(
        userNameTextView: TextView,
        usersTableRef: DatabaseReference
    ) {
        val userId = getUserId()

        usersTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val user: User? = ds.getValue(User::class.java)
                        if (user?.userId == userId){
                            userNameTextView.setText(user?.name)
                            transparentBackground.foreground = null
                        }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

    private fun getUserId(): String?{
        val sharedPreferences = requireContext().getSharedPreferences(
            "user_prefs", AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        return userId
    }

    private fun setListView(listView: ListView){
        val availableButtons = arrayListOf("Навыки", "Новые заказы")

        val listViewAdapter = ArrayAdapter(
            requireContext(),
            R.layout.profile_fragment_available_buttons_list_view,
            R.id.profile_fragment_available_buttons_list_view_element,
            availableButtons)
        listView.adapter = listViewAdapter

        setListViewClick(listView)
    }

    private fun setListViewClick(listView: ListView){
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = listView.adapter.getItem(position).toString()
            when(selectedItem){
                "Навыки" -> {
                    view?.findNavController()?.navigate(R.id.action_profileFragment_to_skillsFragment)
                }
                "Новые заказы" -> {
                    view?.findNavController()?.navigate(R.id.action_profileFragment_to_mechanicsFragment)
                }
            }
        }
    }


    private fun setImageViewOnClick(imageView: ImageView) {
        imageView.setOnClickListener {
            if (it.equals(imageView)){
                Toast.makeText(requireContext(), "Выберите изображение", Toast.LENGTH_LONG).show()
                openGallery()
                //changeImage(imageView)
            }
        }
    }

    // Метод для открытия галереи
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST)
    }

    // Метод для обработки результатов выбора изображения из галереи
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!

            try {
                // Преобразование выбранного изображения в Bitmap
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver, selectedImageUri)

                // Установка выбранного изображения в ImageView
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}