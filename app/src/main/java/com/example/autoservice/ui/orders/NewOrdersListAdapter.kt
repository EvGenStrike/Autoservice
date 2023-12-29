package com.example.autoservice.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.allViews
import com.example.autoservice.R

class NewOrdersListAdapter(
    private val context: Context,
    private val ordersList: java.util.ArrayList<Order>
) : BaseAdapter() {
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
            convertView = LayoutInflater.from(context).inflate(
                R.layout.new_orders_list_item,
                parent,
                false
            )
        }
        val curOrder = ordersList[position]
        val orderTextViews = convertView?.allViews?.filterIsInstance<TextView>()
            ?.filter { view ->
                view.id != View.NO_ID &&
                        view.resources.getResourceName(view.id)
                            .contains("text", ignoreCase = true)
            }?.toList()
        Order::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = field.get(curOrder)?.toString() ?: "Нет данных"
            orderTextViews?.forEach { textView ->
                val idName =
                    convertView!!.context.resources.getResourceName(textView.id).lowercase()
                val fieldName = field.name.lowercase().let {
                    if (it.contains("car")) it.replace("car", "") else it
                }

                val splitIdName = idName
                    .split(Regex("[/_]"))[if (idName.contains("car")) 2 else 1]

                if (fieldName.contains(splitIdName)) {
                    textView.text = value
                    return@forEach
                }
            }
        }
        return convertView
    }

}