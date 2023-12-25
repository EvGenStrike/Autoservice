package com.example.autoservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autoservice.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration: AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_main)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_password)
        val userEmail: EditText = findViewById(R.id.user_email)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {

            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val email = userEmail.text.toString().trim()

            if (login == "" || pass == "" || email == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                // Собдание таблицы user с ключом userId в бд
                val userId = dbRef.push().key!!

                val user = User(userId, login, pass, email)
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        dbRef.child(userId).setValue(user)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                            }
                        userLogin.text.clear()
                        userPass.text.clear()
                        userEmail.text.clear()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(
                            this, "Пользователь не добавлен ", Toast.LENGTH_LONG).show()
                    }
                    /* val db = DbHelper(this, null)
                    db.addUser(user)
                    Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()*/
                }
            }
        }
    }
}