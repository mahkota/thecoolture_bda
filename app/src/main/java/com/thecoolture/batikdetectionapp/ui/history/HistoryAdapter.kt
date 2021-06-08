package com.thecoolture.batikdetectionapp.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thecoolture.batikdetectionapp.data.database.History
import com.thecoolture.batikdetectionapp.databinding.ItemsHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var listHistory = ArrayList<History>()

    fun setHistory(history: List<History>?) {
        if (history == null) return
        this.listHistory.clear()
        this.listHistory.addAll(history)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val itemsHistoryBinding = ItemsHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(itemsHistoryBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = listHistory[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int = listHistory.size

    class HistoryViewHolder(private val binding: ItemsHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            val patternText = "Pattern: ${history.batikName}"
            val timeText = "Scanned on: ${history.scanDateTime}"

            with(binding) {
                tvItemPatternName.text = patternText
                tvItemDate.text = timeText
            }
        }
    }
}