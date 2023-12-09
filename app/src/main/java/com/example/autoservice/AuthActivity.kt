package com.example.autoservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val userPass: EditText = findViewById(R.id.user_password)
        val userPhone: EditText = findViewById(R.id.user_telephone)
        val button: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        linkToReg.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }


        button.setOnClickListener {
            val pass = userPass.text.toString().trim()
            val phone = userPhone.text.toString().trim()


            if (pass == "" || phone == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else{
                val db = DbHelper(this, null)
                val isAuth = db.getUser(pass, phone)

                if (isAuth){
                    Toast.makeText(this, "Пользователь авторизовон", Toast.LENGTH_LONG).show()
                    userPass.text.clear()
                    userPhone.text.clear()

                    val intent = Intent (this, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                    Toast.makeText(this, "Пользователь не авторизовон", Toast.LENGTH_LONG).show()
            }

        }
    }
}