package com.example.autoservice.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.autoservice.R
import com.example.autoservice.ui.mechanics.Mechanic

class Profile_OrderAdapter(
    private val context: Context,
    private val ordersList: ArrayList<Order>,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<Profile_OrderAdapter.Profile_OrderViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Profile_OrderViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_profile_order_item, parent, false)
        return Profile_OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: Profile_OrderViewHolder, position: Int) {
        val order = ordersList[position]
        holder.bind(order)

        holder.cardView.setOnClickListener {
            val visibilityExpandableLayout =
                if (holder.expandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            holder.expandableLayout.visibility = visibilityExpandableLayout

            // Вызываем onItemClick, если слушатель установлен
            onItemClickListener?.onItemClick(order)
        }
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    inner class Profile_OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.profile_fragment_order_card_view)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.profile_fragment_order_linear_layout)
        val orderNameTextView: TextView = itemView.findViewById(R.id.profile_fragment_order_name)
        val orderDescriptionTextView: TextView = itemView.findViewById(R.id.profile_fragment_order_description)

        fun bind(order: Order) {
            orderNameTextView.text = order.orderName
            orderDescriptionTextView.text = order.descriptionProblem

            setupMaterialCardView(itemView as FrameLayout)
            setupChooseMechanicButton(itemView as FrameLayout)
        }
    }

    private fun setupMaterialCardView(frameLayout: FrameLayout){
        val cardView: CardView =
            frameLayout.findViewById(R.id.profile_fragment_order_card_view)
        val expandableLayout: LinearLayout = frameLayout.findViewById(R.id.profile_fragment_order_linear_layout)
        cardView.setOnClickListener {
            val visibilityExpandableLayout =
                if (expandableLayout.visibility == View.GONE) View.VISIBLE
                else View.GONE
            expandableLayout.visibility = visibilityExpandableLayout
        }
    }

    private fun setupChooseMechanicButton(frameLayout: FrameLayout) {
        //временный массив, потом будут браться из бд
        val mechanicList: ArrayList<Mechanic> = arrayListOf(
            Mechanic(
                "Иван", "Иванов", "Иванович",
                "+79001989301", 5),
            Mechanic(
                "Дмитрий", "Мартынов", "Борисович",
                "+793247118503", 3),
            Mechanic(
                "Дмитрий", "Соколов", "Борисович",
                "+79385019571", 4)
        )

        val button: AppCompatButton = frameLayout.findViewById(R.id.profile_fragment_choose_mechanic_button)

        button.setOnClickListener {
            // Создаем всплывающее меню
            val popupMenu = PopupMenu(context, button)

            // Наполняем меню элементами из списка механиков
            for (mechanic in mechanicList) {
                popupMenu.menu.add(mechanic.getFullName())
            }

            // Устанавливаем слушатель выбора элемента в меню
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Обрабатываем выбор элемента
                // В данном случае, вы можете выполнить действие в зависимости от выбранного механика
                // например, вы можете передать выбранного механика обратно в активность или фрагмент
                button.text = menuItem.title
                true // возвращаем true, чтобы не закрывать меню после выбора
            }

            // Показываем всплывающее меню
            popupMenu.show()
        }
    }

}