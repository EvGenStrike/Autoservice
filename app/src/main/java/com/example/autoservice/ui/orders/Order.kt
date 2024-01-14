package com.example.autoservice.ui.orders

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.getValue

data class Order(
    val orderName: String,
    val responsibleName: String,
    val clientName: String,
    val carModel: String,
    val carNumber: String,
    val carVin: String,
    val service: String,
    val cost: Double,
    val descriptionProblem: String,
    val startWorkDate: String,
    val endWorkDate: String,
    val comment: String,
    val userId: String = "",
    val report: String = "",
    var starsCount: Int = 0,
    var orderType: OrderType = OrderType.Current
) : java.io.Serializable {

    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0.0,
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        OrderType.Current
    )

    constructor(dataSnapshot: DataSnapshot) : this(
        orderName = dataSnapshot.child("orderName").getValue(String::class.java) ?: "",
        responsibleName = dataSnapshot.child("responsibleName").getValue(String::class.java) ?: "",
        clientName = dataSnapshot.child("clientName").getValue(String::class.java) ?: "",
        carModel = dataSnapshot.child("carModel").getValue(String::class.java) ?: "",
        carNumber = dataSnapshot.child("carNumber").getValue(String::class.java) ?: "",
        carVin = dataSnapshot.child("carVin").getValue(String::class.java) ?: "",
        service = dataSnapshot.child("service").getValue(String::class.java) ?: "",
        cost = dataSnapshot.child("cost").getValue(Double::class.java) ?: 0.0,
        descriptionProblem = dataSnapshot.child("descriptionProblem").getValue(String::class.java)
            ?: "",
        startWorkDate = dataSnapshot.child("startWorkDate").getValue(String::class.java) ?: "",
        endWorkDate = dataSnapshot.child("endWorkDate").getValue(String::class.java) ?: "",
        comment = dataSnapshot.child("comment").getValue(String::class.java) ?: "",
        userId = dataSnapshot.child("userId").getValue(String::class.java) ?: "",
        report = dataSnapshot.child("report").getValue(String::class.java) ?: "",
        starsCount = dataSnapshot.child("starsCount").getValue(Int::class.java) ?: 0,
        orderType = OrderType.valueOf(
            dataSnapshot.child("orderType").getValue(String::class.java) ?: "Current"
        )
    )

    fun rateOrder(starsCount: Int) {
        this.starsCount = starsCount
    }

}

