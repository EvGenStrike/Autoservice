package com.example.autoservice

import com.google.firebase.database.FirebaseDatabase


class User(val userId: String, val login: String, val pass: String, val email: String) {

}
class Table(val indexId: String,val text1: String, val text2: String, val text3: String) {

}

private fun usernameExists(username: String): Boolean {
    val fdbRefer = FirebaseDatabase.getInstance().getReference("Users/$username")
    return fdbRefer != null
}