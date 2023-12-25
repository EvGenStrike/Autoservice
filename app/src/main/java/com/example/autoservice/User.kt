package com.example.autoservice

import com.google.firebase.database.FirebaseDatabase


class User(val userId: String, val login: String, val pass: String, val phone: String) {

}

private fun usernameExists(username: String): Boolean {
    val fdbRefer = FirebaseDatabase.getInstance().getReference("Users/$username")
    return fdbRefer != null
}