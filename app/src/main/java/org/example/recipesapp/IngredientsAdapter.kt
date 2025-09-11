package org.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemIngredientBinding
import java.math.BigDecimal
import android.icu.math.BigDecimal.ROUND_DOWN
import java.math.BigInteger

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
        val countedQuantity = (BigDecimal(ingredient.quantity)
                * (quantity ?: 1).toBigDecimal())
        val roundedQuantity = try {
            countedQuantity.toBigIntegerExact()
        } catch (e: Exception) {
            countedQuantity.setScale(1, ROUND_DOWN)
        }
        viewHolder.descriptionIngredientItem.text = ingredient.description
        viewHolder.quantityIngredientItem.text = if (
            roundedQuantity is BigInteger
            && quantityStringsUnitsOfMeasure.contains(ingredient.unitOfMeasure)
        ) {
            viewHolder.itemView.resources.getQuantityString(
                quantityStringsUnitsOfMeasure[ingredient.unitOfMeasure]
                    ?: R.plurals.unit_of_measure_default,
                roundedQuantity.toInt(), roundedQuantity.toInt()
            )
        } else viewHolder.itemView.resources.getString(
            R.string.quantity_ingredient,
            roundedQuantity.toString(),
            ingredient.unitOfMeasure
        )
    }

    override fun getItemCount(): Int = dataSet.size
}