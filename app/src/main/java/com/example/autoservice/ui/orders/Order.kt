package com.example.autoservice.ui.orders

data class Order(
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
    val comment: String
) : java.io.Serializable {
}

