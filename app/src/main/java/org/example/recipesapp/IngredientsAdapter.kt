package org.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemIngredientBinding

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val descriptionIngredientItem = itemBinding.tvIngredientItemDescription
        val quantityIngredientItem = itemBinding.tvIngredientItemQuantity
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding =
            ItemIngredientBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        viewHolder.descriptionIngredientItem.text = ingredient.description
        viewHolder.quantityIngredientItem.text = viewHolder.itemView.context.getString(
            R.string.ingredient_quantity,
            ingredient.quantity, ingredient.unitOfMeasure
        )
    }

    override fun getItemCount(): Int = dataSet.size
}