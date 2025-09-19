package org.example.recipesapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.R
import org.example.recipesapp.databinding.ItemStageBinding

class MethodAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding: ItemStageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val stage = itemBinding.tvStageTitle
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemStageBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val stage = dataSet[position]
        viewHolder.stage.text = viewHolder.itemView.context.getString(
            R.string.method_stage, position + 1, stage
        )
    }

    override fun getItemCount(): Int = dataSet.size
}