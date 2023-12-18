package com.example.autoservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autoservice.databinding.ActivityMainBinding

class Registration: AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            setContentView(R.layout.registration_main)

            val userLogin: EditText = findViewById(R.id.user_login)
            val userPass: EditText = findViewById(R.id.user_password)
            val userPhone: EditText = findViewById(R.id.user_telephone)
            val button: Button = findViewById(R.id.button_reg)
            val linkToAuth: TextView = findViewById(R.id.link_to_auth)

            linkToAuth.setOnClickListener {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }

            button.setOnClickListener {

                val login = userLogin.text.toString().trim()
                val pass = userPass.text.toString().trim()
                val phone = userPhone.text.toString().trim()


                if (login == "" || pass == "" || phone == "")
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                else{
                    val user = User(login, pass, phone)

                    val db = DbHelper(this, null)
                    db.addUser(user)
                    Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()

                    userLogin.text.clear()
                    userPass.text.clear()
                    userPhone.text.clear()
                    val intent = Intent (this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
}