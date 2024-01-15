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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Profile_OrderAdapter(
    private val context: Context,
    private val ordersList: ArrayList<Order>,
    private val mechanicsList: ArrayList<Mechanic>,
    private val onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<Profile_OrderAdapter.Profile_OrderViewHolder>() {
    private var onResponsibleSelectedListener: OnResponsibleSelectedListener? = null

    interface OnResponsibleSelectedListener {
        fun onResponsibleSelected(order: Order, mechanicName: String)
    }

    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }

    fun setOnResponsibleSelectedListener(listener: OnResponsibleSelectedListener) {
        this.onResponsibleSelectedListener = listener
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
            setupChooseMechanicButton(itemView as FrameLayout, adapterPosition)

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

    private fun setupChooseMechanicButton(frameLayout: FrameLayout, position: Int) {
        //временный массив, потом будут браться из бд
        val button: AppCompatButton = frameLayout.findViewById(R.id.profile_fragment_choose_mechanic_button)

        val order = ordersList[position]
        val responsibleName = order.responsibleName
        if (responsibleName.isNotEmpty()){
            button.text = responsibleName
        }

        button.setOnClickListener {
            // Создаем всплывающее меню
            val popupMenu = PopupMenu(context, button)

            // Наполняем меню элементами из списка механиков
            for (mechanic in mechanicsList) {
                popupMenu.menu.add(mechanic.getFullName())
            }
            var i = 0
            // Устанавливаем слушатель выбора элемента в меню
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Обрабатываем выбор элемента
                // В данном случае, вы можете выполнить действие в зависимости от выбранного механика
                // например, вы можете передать выбранного механика обратно в активность или фрагмент
                button.text = menuItem.title

                setResponsible(menuItem.title.toString(), position)
                true // возвращаем true, чтобы не закрывать меню после выбора
            }

            // Показываем всплывающее меню
            popupMenu.show()
        }
    }

    private fun setResponsible(mechanicName: String, position: Int) {
        var requiredMechanic: Mechanic? = null
        for (mechanic in mechanicsList) {
            if (mechanic.getFullName() == mechanicName) {
                requiredMechanic = mechanic
                break
            }
        }

        requiredMechanic?.let {
            onResponsibleSelectedListener?.onResponsibleSelected(
                ordersList[position],
                it.getFullName())
        }
    }

}