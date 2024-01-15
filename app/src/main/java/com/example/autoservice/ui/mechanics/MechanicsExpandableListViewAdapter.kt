package com.example.autoservice.ui.mechanics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.autoservice.R
import com.example.autoservice.ui.orders.Order


class MechanicsExpandableListViewAdapter internal constructor(
    private val context: Context,
    private val mechanicsList: List<Mechanic>
): BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return mechanicsList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.mechanicsList[groupPosition].orders.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return mechanicsList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.mechanicsList[groupPosition].orders[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val mechanic = getGroup(groupPosition) as Mechanic

        if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.mechanics_list_item, null)
        }
        val mechanicNameTextView = convertView!!.findViewById<TextView>(R.id.mechanics_list_item_name)
        mechanicNameTextView.setText(mechanic.getFullName())

        val mechanicPhoneTextView = convertView.findViewById<TextView>(R.id.mechanics_list_item_phone)
        mechanicPhoneTextView.setText(mechanic.phone)

        val ratingBar = convertView.findViewById(R.id.mechanics_list_item_rating) as RatingBar
        ratingBar.rating = mechanic.starsCount.toFloat()
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val order = getChild(groupPosition, childPosition) as Order

        if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.mechanics_list_item_order_item, null)
        }

        val orderTextView = convertView!!.findViewById<TextView>(R.id.mechanics_list_item_order_name)
        orderTextView.setText(order.orderName)

        val ratingBar = convertView.findViewById(R.id.mechanics_list_item_order_rating) as RatingBar
        ratingBar.rating = order.starsCount.toFloat()

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean{
        return true
    }
}