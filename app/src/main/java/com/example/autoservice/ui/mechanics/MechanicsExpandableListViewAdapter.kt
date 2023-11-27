package com.example.autoservice.ui.mechanics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.example.autoservice.R
import com.example.autoservice.entities.Mechanic
import com.example.autoservice.entities.Order

class MechanicsExpandableListViewAdapter internal constructor(
    private val context: Context,
    private val mechanicsList: List<Mechanic>,
    private val ordersList: HashMap<Mechanic, List<Order>>
): BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return mechanicsList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.ordersList[this.mechanicsList[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return mechanicsList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return ordersList[this.mechanicsList[groupPosition]]!![childPosition]
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

        val mechanicTextView = convertView!!.findViewById<TextView>(R.id.mechanics_list_item_name)
        mechanicTextView.setText(mechanic.getFullName())

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

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean{
        return true
    }
}