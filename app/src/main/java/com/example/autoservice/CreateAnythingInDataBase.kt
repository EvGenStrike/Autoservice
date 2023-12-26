package com.example.autoservice

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAnythingInDataBase: AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_table)


        val textA : EditText = findViewById(R.id.text1)
        val textB : EditText = findViewById(R.id.text2)
        val textC : EditText = findViewById(R.id.text3)
        val button: Button = findViewById(R.id.button_create)
        dbRef = FirebaseDatabase.getInstance().getReference("Table")
        button.setOnClickListener{
            val text1 = textA.text.toString().trim()
            val text2 = textB.text.toString().trim()
            val text3 = textC.text.toString().trim()
            val indexId = dbRef.push().key!!
            val table = Table(indexId, text1, text2, text3)
            dbRef.child(indexId).setValue(table)
        }
    }
}
