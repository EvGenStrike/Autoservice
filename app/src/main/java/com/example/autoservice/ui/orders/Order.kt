package com.example.autoservice.ui.orders

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
    var starsCount: Int = 0,
) : java.io.Serializable {
    fun rateOrder(starsCount: Int){
        this.starsCount = starsCount
    }
}

