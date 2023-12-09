package com.example.autoservice

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.prefs.PreferencesFactory

class DbHelper(val context: Context,var factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,"app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, pass TEXT, phone TEXT )"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User){
        val values = ContentValues()
        values.put("login",user.login)
        values.put("pass",user.pass)
        values.put("phone",user.phone)

        val db = this.writableDatabase
        db.insert("users",null, values)

        db.close()
    }
    fun getUser(pass: String, phone: String): Boolean{
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$pass' AND pass = '$phone'",null)
        return result.moveToFirst()
    }

}