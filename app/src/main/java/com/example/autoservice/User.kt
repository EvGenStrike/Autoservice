package com.example.autoservice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase


class User(
    val userId: String,
    val name: String,
    val pass: String,
    val email: String,
    val phone: String) {
    constructor() : this(
        "",
        "",
        "",
        "",
        ""
    )

    constructor(dataSnapshot: DataSnapshot) : this(
        userId = dataSnapshot.child("userId").getValue(String::class.java) ?: "",
        name = dataSnapshot.child("name").getValue(String::class.java) ?: "",
        pass = dataSnapshot.child("pass").getValue(String::class.java) ?: "",
        email = dataSnapshot.child("email").getValue(String::class.java) ?: "",
        phone = dataSnapshot.child("phone").getValue(String::class.java) ?: ""
    )

    companion object {
        fun getUserId(context: Context): String?{
            val sharedPreferences = context.getSharedPreferences(
                "user_prefs", AppCompatActivity.MODE_PRIVATE)
            val userId = sharedPreferences.getString("user_id", "")
            return userId
        }

        fun isUserMechanic(context: Context): Boolean{
            return getUserId(context) != "-Nms_HXQ6e_YjxMJf-uP"
        }
    }

}
class Table(val indexId: String,val text1: String, val text2: String, val text3: String) {

}

private fun usernameExists(username: String): Boolean {
    val fdbRefer = FirebaseDatabase.getInstance().getReference("Users/$username")
    return fdbRefer != null
}