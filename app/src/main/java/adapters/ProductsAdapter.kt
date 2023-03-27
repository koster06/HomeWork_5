package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.R
import com.example.homework_5.databinding.ProductItemBinding
import retrofit.Product

class ProductsAdapter: ListAdapter <Product,ProductsAdapter.Holder>(Comparator()){

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ProductItemBinding.bind(view)
        fun bind(product: Product) = with(binding){
            nameSimple.text = product.title
            emailSimple.text = product.brand
        }
    }

    class Comparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}