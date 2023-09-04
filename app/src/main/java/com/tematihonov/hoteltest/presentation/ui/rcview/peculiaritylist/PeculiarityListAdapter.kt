package com.tematihonov.hoteltest.presentation.ui.rcview.peculiaritylist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tematihonov.hoteltest.databinding.ItemPeculiarityBinding

class PeculiarityListAdapter(): RecyclerView.Adapter<PeculiarityListAdapter.PeculiarityListViewHolder>() {

    var peculiarities: List<String> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    inner class PeculiarityListViewHolder(val binding: ItemPeculiarityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PeculiarityListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPeculiarityBinding.inflate(inflater, parent, false)
        return PeculiarityListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeculiarityListAdapter.PeculiarityListViewHolder, position: Int) {
        val peculiarity = peculiarities[position]
        with(holder.binding) {
            peculiarityText.text = peculiarity
            Log.d("GGG", "t ${peculiarity}")
        }
    }

    override fun getItemCount(): Int {
        return peculiarities.size
    }
}