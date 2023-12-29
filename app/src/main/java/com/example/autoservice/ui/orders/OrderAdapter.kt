package com.example.autoservice.ui.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.R


class OrderAdapter(mList: List<CurrentOrderViewModel>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val mList: List<CurrentOrderViewModel>
    private lateinit var curOrder: Order

    init {
        this.mList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.current_orders_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val model: CurrentOrderViewModel = mList[position]
        holder.orderTitle.text = "Заказ №%s".format(position + 1)
        curOrder = model.currentOrder
        Order::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = field.get(curOrder)?.toString() ?: "Нет данных"
            holder.orderTextViews.forEach { textView ->
                val idName =
                    holder.itemView.context.resources.getResourceName(textView.id).lowercase()
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
        val isExpandable: Boolean = model.isExpandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        val backColor =
            if (model.isExpandable) holder.linearLayout.context.getColor(R.color.current_orders_open_background_color) else
                holder.linearLayout.context.getColor(R.color.white)
        holder.linearLayout.setBackgroundColor(backColor)

        holder.linearLayout.setOnClickListener {
            model.isExpandable = (!model.isExpandable)
            notifyItemChanged(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayout: LinearLayout
        val expandableLayout: LinearLayout
        val orderTitle: TextView
        var orderTextViews: List<TextView>

        init {
            linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
            expandableLayout = itemView.findViewById<LinearLayout>(R.id.expandable_layout)
            orderTitle = itemView.findViewById(R.id.order_title)
            orderTextViews = ArrayList()
            orderTextViews = itemView.allViews.filterIsInstance<TextView>()
                .filter { view ->
                    view.id != View.NO_ID &&
                            view.resources.getResourceName(view.id)
                                .contains("text", ignoreCase = true)
                }.toList()
        }
    }
}