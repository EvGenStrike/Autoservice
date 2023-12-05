package com.example.autoservice.entities

class Mechanic (
    private val firstName: String,
    private val lastName: String,
    private val patronymic: String,
    private var starsCount: Int = 0){
    fun getFullName(): String {
        return "%s %s %s".format(lastName, firstName, patronymic);
    }

    fun rateMechanic(starsCount: Int){
        this.starsCount = starsCount
    }
}