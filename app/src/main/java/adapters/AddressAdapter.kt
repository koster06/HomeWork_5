package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.databinding.AddressItemBinding
import entities.AddressEntity

class AddressAdapter(private val listener: OnItemClickListener): ListAdapter<AddressEntity, AddressAdapter.Holder>(Comparator()) {

    class Comparator: DiffUtil.ItemCallback<AddressEntity>()  {
        override fun areItemsTheSame(oldItem: AddressEntity, newItem: AddressEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AddressEntity, newItem: AddressEntity): Boolean {
            return oldItem == newItem
        }

    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = AddressItemBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(address: AddressEntity) = with(binding) {
            tvStreet.text = "${address.id} : ${address.street}"
            tvCity.text = address.city
            tvState.text = address.state
            tvZip.text = address.zip
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AddressItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding.root).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val address = getItem(position)
                    listener.onItemClick(address)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AddressAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(address: AddressEntity)
    }

}