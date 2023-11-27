package com.example.autoservice.entities

class Mechanic (val firstName: String, val lastName: String, val patronymic: String){
    fun getFullName(): String {
        return "%s %s %s".format(lastName, firstName, patronymic);
    }
}