package com.example.autoservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


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
        val firebaseAuth = FirebaseAuth.getInstance()

        button.setOnClickListener {
            val pass = userPass.text.toString().trim()
            val email = userEmail.text.toString().trim()

            if (pass == "" || email == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else{
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(){
                    if(it.isSuccessful){
                        Toast.makeText(this, "Пользователь авторизовон", Toast.LENGTH_LONG).show()
                        userPass.text.clear()
                        userEmail.text.clear()
                        val intent = Intent (this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Пользователь не авторизовон", Toast.LENGTH_LONG).show()
                    }
                }
/*                val db = DbHelper(this, null)
                val isAuth = db.getUser(pass, email)

                if (isAuth){
                    Toast.makeText(this, "Пользователь авторизовон", Toast.LENGTH_LONG).show()
                    userPass.text.clear()
                    userEmail.text.clear()

                    val intent = Intent (this, MainActivity::class.java)
                    startActivity(intent)*/
            }
                /*else
                    Toast.makeText(this, "Пользователь не авторизовон", Toast.LENGTH_LONG).show()*/

        }

    }
}