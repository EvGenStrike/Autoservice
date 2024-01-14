package com.example.autoservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autoservice.ui.mechanics.Mechanic
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration: AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRefMechanics: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        autoLogin()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val linkToAuth:TextView = findViewById(R.id.link_to_auth)
        createUser()

//        fun setupNavView(){
//            val bottomNavigationView: BottomNavigationView = binding.bottomNavView
//            val navController = findNavController(R.id.nav_host_fragment_activity_main)
//            val appBarConfiguration = AppBarConfiguration(
//                setOf(
//                    R.id.navigation_orders,
//                    R.id.navigation_mechanics,
//                    R.id.navigation_profile
//                )
//            )
//            setupActionBarWithNavController(navController, appBarConfiguration)
//            bottomNavigationView.setupWithNavController(navController)
//
//            bottomNavigationView.itemIconTintList = null
//        }

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser(){
        val userPass: EditText = findViewById(R.id.user_password)
        val userEmail: EditText = findViewById(R.id.user_email)
        val button: Button = findViewById(R.id.button_reg)
        val userName: EditText = findViewById(R.id.user_login)
        val userPhone: EditText = findViewById(R.id.user_phone)

        button.setOnClickListener {
            val pass = userPass.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val name = userName.text.toString().trim()
            val phone = userPhone.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            dbRef = FirebaseDatabase.getInstance().getReference("Users")
                            val userId = dbRef.push().key!!
                            val user = User(userId, name, pass, email, phone)
                            dbRef.child(userId).setValue(user)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Пользователь $name добавлен", Toast.LENGTH_LONG).show()
                                }

                            dbRefMechanics = FirebaseDatabase.getInstance().getReference("Mechanics")
                            val splitted_name = name.split(" ")
                            val mechanic = Mechanic(
                                splitted_name[0],
                                splitted_name[1],
                                splitted_name[2],
                                phone,
                                0,
                                ArrayList())
                            dbRefMechanics.child(userId).setValue(mechanic)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Механик $name добавлен", Toast.LENGTH_LONG).show()
                                }

                            userName.text.clear()
                            userPass.text.clear()
                            userEmail.text.clear()
                            userPhone.text.clear()

                            saveUserToken(userId)

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this, "Пользователь не добавлен ", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
            else{
                Toast.makeText(
                    this, "Проверьте email и пороль", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveUserToken(userId: String?) {
        // Сохранение токена в SharedPreferences или другом месте для последующего использования
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_id", userId)
        editor.apply()
    }

    private fun autoLogin() {
        // Получение токена из SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)

        // Проверка наличия токена и автоматическая аутентификация
        if (userId != null) {
            // Автоматическая аутентификация с использованием токена
            // Например, firebaseAuth.signInWithCustomToken(token)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}