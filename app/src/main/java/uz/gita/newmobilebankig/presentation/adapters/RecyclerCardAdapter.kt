package uz.gita.newmobilebankig.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem

class RecyclerCardAdapter : ListAdapter<DataItem, RecyclerCardAdapter.ViewHolder>(DiffItem) {
    private var editListener: ((String) -> Unit)? = null
    private var listener: ((DataItem) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardName: EditText = view.findViewById<EditText>(R.id.cardName)
        private val cardNumber: TextView = view.findViewById<TextView>(R.id.cardNumber)
        private val expireDate: TextView = view.findViewById<TextView>(R.id.expireDate)
        private val amountMoney: TextView = view.findViewById<TextView>(R.id.amountMoney)

        init {
            itemView.findViewById<ImageButton>(R.id.editButton).setOnClickListener {
                editListener?.invoke(getItem(absoluteAdapterPosition).pan)
            }
            itemView.setOnClickListener {
                listener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun bind() {
            val data: DataItem = getItem(absoluteAdapterPosition)
            cardName.setText(data.cardName)
            cardNumber.text = data.pan
            expireDate.text = data.exp
            amountMoney.text = data.balance.toString()
        }
    }

    object DiffItem : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    fun setListener(block: (DataItem) -> Unit) {
        listener = block
    }
}