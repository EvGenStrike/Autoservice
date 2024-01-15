package com.example.autoservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private lateinit var auth: FirebaseAuth

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val userPass: EditText = findViewById(R.id.user_password)
        val userEmail: EditText = findViewById(R.id.user_email)
        val button: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)


        linkToReg.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()

        button.setOnClickListener {
            val pass = userPass.text.toString().trim()
            val email = userEmail.text.toString().trim()

            if (email.isNotEmpty()&& pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Пользователь авторизован", Toast.LENGTH_LONG).show()
                        userPass.text.clear()
                        userEmail.text.clear()

                        val user = getUserByEmail(email)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Пользователь не авторизовон", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveUserToken(user: User) {
        // Сохранение токена в SharedPreferences или другом месте для последующего использования
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_id", user.userId)
        editor.apply()
    }

    fun getUserByEmail(userEmail: String): User? {
        val usersTableRef = FirebaseDatabase.getInstance().reference.child("Users")

        var requiredUser: User? = null

        usersTableRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    runOnUiThread {
                        for (ds in dataSnapshot.children) {
                            val user: User? = ds.getValue(User::class.java)
                            if (user?.email == userEmail) {
                                requiredUser = user
                                saveUserToken(user)
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })

        return requiredUser
    }
}