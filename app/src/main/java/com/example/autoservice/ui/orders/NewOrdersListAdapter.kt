package com.example.autoservice.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.autoservice.R

class NewOrdersListAdapter(private val context: Context, private val ordersList: java.util.ArrayList<Order>) : BaseAdapter() {
    private lateinit var serialNum: TextView
    private lateinit var name: TextView
    private lateinit var contactNum: TextView
    override fun getCount(): Int {
        return ordersList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.new_orders_list_item, parent, false)
        }
        return convertView
    }

}