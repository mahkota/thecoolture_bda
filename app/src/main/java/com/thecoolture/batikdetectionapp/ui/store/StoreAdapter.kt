package com.thecoolture.batikdetectionapp.ui.store

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thecoolture.batikdetectionapp.data.StoreEntity
import com.thecoolture.batikdetectionapp.databinding.ItemsStoreBinding
import java.util.*
import kotlin.collections.ArrayList

class StoreAdapter : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    private var listStores = ArrayList<StoreEntity>()

    fun setStores(stores: List<StoreEntity>?) {
        if (stores == null) return
        this.listStores.clear()
        this.listStores.addAll(stores)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreAdapter.StoreViewHolder {
        val itemsStoreBinding = ItemsStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(itemsStoreBinding)
    }

    override fun onBindViewHolder(holder: StoreAdapter.StoreViewHolder, position: Int) {
        val store = listStores[position]
        holder.bind(store)
    }

    override fun getItemCount(): Int = listStores.size

    class StoreViewHolder(private val binding: ItemsStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: StoreEntity) {
            with(binding) {
                tvItemName.text = store.storeName
                tvItemLocation.text = store.region
                tvItemPatterns.text = store.batiks?.joinToString()
                itemView.setOnClickListener{
//                    val gmnIntentUri = Uri.parse("geo:${store.location.latitude}, ${store.location.longitude}")
                    val gmnIntentUri = Uri.parse(String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:${store.location.latitude},${store.location.longitude}"))
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
//                    mapIntent.setPackage("com.google.android.apps.maps")
                    itemView.context.startActivity(mapIntent)
                }
            }
        }
    }
}