package com.example.autoservice.ui.orders
import androidx.lifecycle.ViewModel

class CurrentOrderViewModel(currentOrder: Order) {
    private var _isExpandable = false
    private var _currentOrder: Order
    var isExpandable: Boolean
        get() = _isExpandable
        set(value) {
            _isExpandable = value
        }
    var currentOrder: Order
        get() = _currentOrder
        set(value) {
            _currentOrder = value
        }
    init {
        _currentOrder = currentOrder
    }
}