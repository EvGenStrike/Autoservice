package com.example.autoservice.ui.mechanics

import com.example.autoservice.ui.orders.Order

class Mechanic(
    private val firstName: String,
    private val lastName: String,
    private val patronymic: String,
    public val phone: String,
    private val _starsCount: Int = 0,
    private val orders: List<Order> = ArrayList()
){
    var starsCount: Int = _starsCount
        private set

    fun getFullName(): String {
        return "%s %s %s".format(lastName, firstName, patronymic);
    }

    fun rateMechanic(starsCount: Int){
        this.starsCount = starsCount
    }

    fun addOrder(order: Order){
        (orders as ArrayList).add(order)
    }

    fun getOrders(): List<Order>{
        return orders
    }

    fun getOrder(orderName: String): Order{
        return orders.first { order: Order -> order.orderName == orderName }
    }
}