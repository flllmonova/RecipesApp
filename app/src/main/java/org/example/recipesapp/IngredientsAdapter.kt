package org.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemIngredientBinding
import kotlin.math.roundToInt

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity: Int? = null

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

    private val quantityStringsUnitsOfMeasure = mapOf(
        UNIT_OF_MEASURE_LEAF to R.plurals.unit_of_measure_leaf,
        UNIT_OF_MEASURE_SLICE to R.plurals.unit_of_measure_slice,
        UNIT_OF_MEASURE_TEA_SPOON to R.plurals.unit_of_measure_tea_spoon,
        UNIT_OF_MEASURE_TABLE_SPOON to R.plurals.unit_of_measure_table_spoon,
    )

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
        val res = viewHolder.itemView.resources
        val countedQuantity = (ingredient.quantity.toFloatOrNull() ?: 1f) * (quantity ?: 1)
        val quantityString = if (countedQuantity % 1 == 0f
            && quantityStringsUnitsOfMeasure.keys.contains(ingredient.unitOfMeasure)
        ) {
            res.getQuantityString(
                quantityStringsUnitsOfMeasure[ingredient.unitOfMeasure]!!,
                countedQuantity.roundToInt(), countedQuantity.roundToInt()
            )
        } else res.getString(
            R.string.quantity_ingredient,
            if (countedQuantity % 1 == 0f) countedQuantity.roundToInt()
            else countedQuantity,
            ingredient.unitOfMeasure
        )

        viewHolder.descriptionIngredientItem.text = ingredient.description
        viewHolder.quantityIngredientItem.text = quantityString
    }

    override fun getItemCount(): Int = dataSet.size
}