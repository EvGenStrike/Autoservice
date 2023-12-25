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
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.autoservice.R
import com.example.autoservice.databinding.FragmentProfileBinding
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
        setImageViewOnClick(binding.profileFragmentProfilePicture)

        return root
    }

    private fun setImageViewOnClick(imageView: ImageView) {
        imageView.setOnClickListener {
            if (it.equals(imageView)){
                Toast.makeText(requireContext(), "asdf", Toast.LENGTH_LONG).show()
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

//    private fun changeImage(imageView: ImageView){
//        Glide.with(this)
//            .asBitmap()
//            .load(imageUrl)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    // Создаем RoundedBitmapDrawable
//                    val roundedDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
//                    roundedDrawable.cornerRadius = 10f // радиус скругления углов
//                    roundedDrawable.setAntiAlias(true)
//
//                    // Добавляем контур
//                    addBorderToDrawable(roundedDrawable, 5, Color.RED) // ширина и цвет контура
//
//                    // Устанавливаем Drawable в ImageView
//                    imageView.setImageDrawable(roundedDrawable)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Можно добавить обработку, если не удалось загрузить изображение
//                }
//            })
//    }
//
//    private fun addBorderToDrawable(drawable: Drawable, borderWidth: Int, borderColor: Int) {
//        val paint = Paint()
//        paint.style = Paint.Style.STROKE
//        paint.strokeWidth = borderWidth.toFloat()
//        paint.color = borderColor
//        paint.isAntiAlias = true
//
//        val borderDrawable = BitmapDrawable(resources, (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true))
//        val canvas = android.graphics.Canvas(borderDrawable.bitmap)
//        val borderRect = android.graphics.RectF(0f, 0f, borderDrawable.intrinsicWidth.toFloat(), borderDrawable.intrinsicHeight.toFloat())
//        canvas.drawRoundRect(borderRect, drawable.cornerRadius, drawable.cornerRadius, paint)
//
//        drawable.setBounds(0, 0, borderDrawable.intrinsicWidth, borderDrawable.intrinsicHeight)
//        drawable.draw(canvas)
//    }
}