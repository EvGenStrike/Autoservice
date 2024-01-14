package com.example.autoservice.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.autoservice.R
import com.example.autoservice.databinding.FragmentProfileBinding
import com.example.autoservice.ui.mechanics.MechanicsExpandableListViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.io.IOException


class ProfileFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageView: ImageView

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val profileImageView = binding.profileFragmentProfilePicture
        imageView = profileImageView
        val avatarName = getAvatarName()
        if (avatarName != "") {
            val storageReference = Firebase.storage.getReference("avatars/$avatarName")

            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(requireContext())
                    .load(uri)
                    .into(imageView)
            }.addOnFailureListener {
                it.printStackTrace()
            }
        }
        setImageViewOnClick(binding.profileFragmentProfilePicture)
        setListView(binding.profileFragmentAvailableButtonsListView)

        return root
    }

    private fun setListView(listView: ListView) {
        val availableButtons = arrayListOf("Навыки", "Новые заказы")

        val listViewAdapter = ArrayAdapter(
            requireContext(),
            R.layout.profile_fragment_available_buttons_list_view,
            R.id.profile_fragment_available_buttons_list_view_element,
            availableButtons
        )
        listView.adapter = listViewAdapter

        setListViewClick(listView)
    }

    private fun setListViewClick(listView: ListView) {
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = listView.adapter.getItem(position).toString()
            when (selectedItem) {
                "Навыки" -> {
                    view?.findNavController()
                        ?.navigate(R.id.action_profileFragment_to_skillsFragment)
                }

                "Новые заказы" -> {
                    view?.findNavController()
                        ?.navigate(R.id.action_profileFragment_to_mechanicsFragment)
                }
            }
        }
    }


    private fun setImageViewOnClick(imageView: ImageView) {
        imageView.setOnClickListener {
            if (it.equals(imageView)) {
                Toast.makeText(
                    requireContext(),
                    "Не переключайтесь на другой экран, идет загрузка фотографии в базу данных",
                    Toast.LENGTH_LONG
                ).show()
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
            Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST
        )
    }

    // Метод для обработки результатов выбора изображения из галереи
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            try {
                // Преобразование выбранного изображения в Bitmap
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver, selectedImageUri
                )

                // Установка выбранного изображения в ImageView
                saveImageToFirebaseStorage(bitmap)
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun getUserId(): String? {
        val sharedPreferences = requireContext().getSharedPreferences(
            "user_prefs", AppCompatActivity.MODE_PRIVATE
        )
        val userId = sharedPreferences.getString("user_id", "")
        return userId
    }

    private fun getAvatarName(): String? {
        val sharedPreferences = requireContext().getSharedPreferences(
            "user_prefs", AppCompatActivity.MODE_PRIVATE
        )
        val avatar = sharedPreferences.getString("avatar", "")
        return avatar
    }

    private fun saveImageToFirebaseStorage(bitmap: Bitmap) {
        val storageRef = Firebase.storage.reference
        val avatarRef =
            storageRef.child("avatars/avatar${getUserId()}.jpg") // Путь к файлу в облачном хранилище

        // Конвертируем Bitmap в ByteArray
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val data = stream.toByteArray()

        // Загружаем данные в Firebase Storage
        avatarRef.putBytes(data)
            .addOnSuccessListener { taskSnapshot ->
                // Успешно загружено
                // taskSnapshot.metadata содержит информацию о загруженном файле
                val sharedPreferences = requireContext().getSharedPreferences(
                    "user_prefs",
                    AppCompatActivity.MODE_PRIVATE
                )
                val editor = sharedPreferences.edit()
                editor.putString("avatar", taskSnapshot.metadata?.name)
                editor.apply()
            }
            .addOnFailureListener { e ->
                // Произошла ошибка
            }
    }

}