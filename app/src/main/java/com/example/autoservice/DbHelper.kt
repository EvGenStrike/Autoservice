package com.example.autoservice

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DbHelper(val context: Context, var factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,"app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, pass TEXT, phone TEXT )"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

//    fun getUser(pass: String, phone: String): Boolean{
//        val result = FirebaseDatabase.getInstance().getReference(("User/$phone"))
//            return (result != null);
//
//    }

    fun tableToString(db: SQLiteDatabase, tableName: String): String? {
        Log.d("", "tableToString called")
        var tableString: String? = String.format("Table %s:\n", tableName)
        val allRows = db.rawQuery("SELECT * FROM $tableName", null)
        tableString += cursorToString(allRows)
        return tableString
    }

    fun cursorToString(cursor: Cursor): String? {
        var cursorString = ""
        if (cursor.moveToFirst()) {
            val columnNames = cursor.columnNames
            for (name in columnNames) cursorString += String.format("%s ][ ", name)
            cursorString += "\n"
            do {
                for (name in columnNames) {
                    cursorString += String.format(
                        "%s ][ ",
                        cursor.getString(cursor.getColumnIndex(name))
                    )
                }
                cursorString += "\n"
            } while (cursor.moveToNext())
        }
        return cursorString
    }
}