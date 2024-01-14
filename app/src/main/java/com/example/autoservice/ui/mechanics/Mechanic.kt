package com.example.autoservice.ui.mechanics

import com.example.autoservice.ui.orders.Order
import com.example.autoservice.ui.orders.OrderType
import com.google.firebase.database.DataSnapshot

data class Mechanic(
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val phone: String,
    var starsCount: Int = 0,
    val orders: ArrayList<Order> = ArrayList()
) : java.io.Serializable {

    constructor() : this(
        "",
        "",
        "",
        "",
        0,
        ArrayList()
    )

    constructor(dataSnapshot: DataSnapshot) : this(
        firstName = dataSnapshot.child("firstName").getValue(String::class.java) ?: "",
        lastName = dataSnapshot.child("lastName").getValue(String::class.java) ?: "",
        patronymic = dataSnapshot.child("patronymic").getValue(String::class.java) ?: "",
        phone = dataSnapshot.child("phone").getValue(String::class.java) ?: "",
        starsCount = dataSnapshot.child("starsCount").getValue(Int::class.java) ?: 0
    )

    fun getFullName(): String {
        return "%s %s %s".format(lastName, firstName, patronymic);
    }

    fun rateMechanic(starsCount: Int){
        this.starsCount = starsCount
    }

    fun addOrder(order: Order){
        (orders as ArrayList).add(order)
    }

    fun getOrder(orderName: String): Order{
        return orders.first { order: Order -> order.orderName == orderName }
    }
}