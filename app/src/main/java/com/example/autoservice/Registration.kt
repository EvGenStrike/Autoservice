package com.example.autoservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration: AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

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
        val userLogin: EditText = findViewById(R.id.user_login)

        button.setOnClickListener {
            val pass = userPass.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val login = userLogin.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            dbRef = FirebaseDatabase.getInstance().getReference("Users")
                            val userId = dbRef.push().key!!
                            val user = User(userId, login, pass, email)
                            dbRef.child(userId).setValue(user)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                                }
                            userLogin.text.clear()
                            userPass.text.clear()
                            userEmail.text.clear()
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
}